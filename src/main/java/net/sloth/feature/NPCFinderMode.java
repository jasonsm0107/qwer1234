package net.sloth.feature;

import com.pixelmonmod.pixelmon.entities.npcs.NPCChatting;
import com.pixelmonmod.pixelmon.entities.npcs.NPCEntity;
import com.pixelmonmod.pixelmon.entities.npcs.NPCFisherman;
import com.pixelmonmod.pixelmon.entities.npcs.NPCNurseJoy;
import com.pixelmonmod.pixelmon.entities.npcs.NPCQuestGiver;
import com.pixelmonmod.pixelmon.entities.npcs.NPCRelearner;
import com.pixelmonmod.pixelmon.entities.npcs.NPCShopkeeper;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrader;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import net.sloth.util.PUFeatureColors;
import net.sloth.util.PUSettings;
import net.sloth.util.PUTranslation;
import java.awt.Color;
import net.minecraft.util.text.TranslationTextComponent;

public enum NPCFinderMode implements IFeatureStatus {
    OFF(NPCEntity.class, "", false),
    ALL(NPCEntity.class, "all", true),
    CHATTING(NPCChatting.class, "chatting", true),
    FISHER(NPCFisherman.class, "fisher", true),
    NURSE(NPCNurseJoy.class, "nurse", true),
    QUEST_GIVER(NPCQuestGiver.class, "quest", true),
    RELEARNER(NPCRelearner.class, "relearner", true),
    SHOPKEEPER(NPCShopkeeper.class, "shopkeeper", true),
    TRADER(NPCTrader.class, "trader", true),
    TRAINER(NPCTrainer.class, "trainer", true);

    private final Class<? extends NPCEntity> clazz;
    private final String statusText;
    private final boolean enabled;

    public TranslationTextComponent getStatusText() {
        return this != OFF ? PUTranslation.of(String.format("npcfinder.status.%s", this.statusText), new Object[0]) : null;
    }

    public Color getColor() {
        return PUFeatureColors.NPC_FINDER;
    }

    public void next() {
        switch (this) {
            case OFF:
                PUSettings.setNpcFinderMode(ALL);
                break;
            case ALL:
                PUSettings.setNpcFinderMode(CHATTING);
                break;
            case CHATTING:
                PUSettings.setNpcFinderMode(FISHER);
                break;
            case FISHER:
                PUSettings.setNpcFinderMode(NURSE);
                break;
            case NURSE:
                PUSettings.setNpcFinderMode(QUEST_GIVER);
                break;
            case QUEST_GIVER:
                PUSettings.setNpcFinderMode(RELEARNER);
                break;
            case RELEARNER:
                PUSettings.setNpcFinderMode(SHOPKEEPER);
                break;
            case SHOPKEEPER:
                PUSettings.setNpcFinderMode(TRADER);
                break;
            case TRADER:
                PUSettings.setNpcFinderMode(TRAINER);
                break;
            case TRAINER:
                PUSettings.setNpcFinderMode(OFF);
        }

    }

    public boolean isValid(NPCEntity entity) {
        boolean result = this.clazz.isInstance(entity);
        return result;
    }

    private NPCFinderMode(Class clazz, String statusText, boolean enabled) {
        this.clazz = clazz;
        this.statusText = statusText;
        this.enabled = enabled;
    }

    public Class<? extends NPCEntity> getClazz() {
        return this.clazz;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
