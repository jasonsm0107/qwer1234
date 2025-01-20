package net.sloth.handler;

import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.client.gui.battles.BattleScreen;
import com.pixelmonmod.pixelmon.client.gui.battles.ClientBattleManager;
import com.pixelmonmod.pixelmon.client.gui.battles.PixelmonClientData;
import com.pixelmonmod.pixelmon.client.storage.ClientStorageManager;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.ChooseAttackPacket;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.sloth.config.PUConfig;
import net.sloth.feature.AutoBattleMode;
import net.sloth.util.PUSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MutableBoundingBox;

public class PUAutobattleHandler {
    public PUAutobattleHandler() {
    }

    public static void onCallback(ClientBattleManager bm, BattleScreen parent) {
        if (!PUSettings.getAutoBattleMode().equals(AutoBattleMode.OFF)) {
            List<PixelmonClientData> targetedPokemon = new ArrayList();

            for(int i = 0; i < bm.targetted[1].length; ++i) {
                if (bm.targetted[1][i]) {
                    targetedPokemon.add(bm.displayedEnemyPokemon[i]);
                }
            }

            boolean isBlacklisted = ((List<String>)PUConfig.INSTANCE.battleBlacklist.get()).stream()
                    .map(value -> {
                        try {
                            return Integer.parseInt(value); // String -> Integer 변환
                        } catch (Exception e) {
                            return -1; // 변환 실패 시 -1 반환
                        }
                    })
                    .filter(integer -> integer > 0) // 유효한 값만 필터링
                    .map(value -> Optional.ofNullable(PixelmonSpecies.fromDex(value))) // Optional로 래핑
                    .filter(Optional::isPresent) // Optional 값 필터링
                    .map(Optional::get) // Optional에서 값 추출
                    .anyMatch(species -> targetedPokemon.stream()
                            .map(pixelmonClientData -> pixelmonClientData.species)
                            .anyMatch(species::equals)); // 단일 Species 비교

            if (!isBlacklisted) {
                List<PixelmonEntity> enemyPixelmonEntities = (List)targetedPokemon.stream().flatMap((pixelmonClientData) -> {
                    return Minecraft.getInstance().level.getEntitiesOfClass(PixelmonEntity.class, AxisAlignedBB.of(MutableBoundingBox.createProper(Minecraft.getInstance().player.blockPosition().getX() - 32, Minecraft.getInstance().player.blockPosition().getY() - 64, Minecraft.getInstance().player.blockPosition().getZ() - 32, Minecraft.getInstance().player.blockPosition().getX() + 32, Minecraft.getInstance().player.blockPosition().getY() + 64, Minecraft.getInstance().player.blockPosition().getZ() + 32)), (pixelmonEntity) -> {
                        return pixelmonEntity.getSpecies().is(new Species[]{pixelmonClientData.species});
                    }).stream().filter((pixelmonEntity) -> {
                        return pixelmonEntity.getUUID().equals(pixelmonClientData.pokemonUUID);
                    });
                }).collect(Collectors.toList());
                boolean flag;
                if ((Boolean)PUConfig.INSTANCE.pauseOnShinys.get()) {
                    flag = enemyPixelmonEntities.stream().filter((pixelmonEntity) -> {
                        return !pixelmonEntity.isBossPokemon();
                    }).filter((pixelmonEntity) -> {
                        return pixelmonEntity.getOwner() == null;
                    }).anyMatch((pixelmonEntity) -> {
                        return pixelmonEntity.getPokemon().isShiny();
                    });
                    if (flag) {
                        return;
                    }
                }

                if ((Boolean)PUConfig.INSTANCE.pauseOnLegendaries.get()) {
                    flag = enemyPixelmonEntities.stream().filter((pixelmonEntity) -> {
                        return pixelmonEntity.getOwner() == null;
                    }).anyMatch((pixelmonEntity) -> {
                        return pixelmonEntity.getPokemon().isLegendary();
                    });
                    if (flag) {
                        return;
                    }
                }

                if ((Boolean)PUConfig.INSTANCE.pauseOnUltraBeasts.get()) {
                    flag = enemyPixelmonEntities.stream().filter((pixelmonEntity) -> {
                        return pixelmonEntity.getOwner() == null;
                    }).anyMatch((pixelmonEntity) -> {
                        return pixelmonEntity.getPokemon().isUltraBeast();
                    });
                    if (flag) {
                        return;
                    }
                }

                if ((Boolean)PUConfig.INSTANCE.pauseOnUncaught.get()) {
                    flag = enemyPixelmonEntities.stream().filter((pixelmonEntity) -> {
                        return pixelmonEntity.getOwner() == null;
                    }).anyMatch((pixelmonEntity) -> {
                        return !ClientStorageManager.pokedex.hasCaught(pixelmonEntity.getSpecies());
                    });
                    if (flag) {
                        return;
                    }
                }

                PixelmonClientData data = bm.getCurrentPokemon();

                for(int i = 0; i < data.moveset.attacks.length; ++i) {
                    Attack attack = data.moveset.attacks[i];
                    if (attack.pp > 0) {
                        parent.setTargeting(data, attack, -1, -1);
                        bm.selectedActions.add(new ChooseAttackPacket(data.pokemonUUID, bm.targetted, i, bm.battleControllerIndex, bm.megaEvolving, bm.dynamaxing));
                        bm.selectedMove();
                        break;
                    }
                }

            }
        }
    }
}
