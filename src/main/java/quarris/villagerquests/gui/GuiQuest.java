package quarris.villagerquests.gui;

import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quarris.villagerquests.VillagerQuests;
import quarris.villagerquests.capability.CapabilityHandler;
import quarris.villagerquests.capability.IQuestHandler;
import quarris.villagerquests.gui.container.ContainerQuest;
import quarris.villagerquests.quest.IQuest;
import quarris.villagerquests.quest.task.ITask;
import quarris.villagerquests.quest.task.ItemTask;

@SideOnly(Side.CLIENT)
public class GuiQuest extends GuiContainer {

    protected static final ResourceLocation TEXTURE = VillagerQuests.createRes("textures/gui/villager_quest.png");
    protected int page;
    protected EntityPlayer player;
    protected EntityVillager villager;
    protected GuiButtonImage buttonLeft;
    protected GuiButtonImage buttonRight;

    public GuiQuest(EntityPlayer player, int entityID, int page) {
        super(new ContainerQuest());
        this.player = player;
        this.page = page;

        villager = (EntityVillager) player.world.getEntityByID(entityID);
    }

    @Override
    public void initGui() {
        this.xSize = 176;
        this.ySize = 196;
        super.initGui();
        this.buttonLeft = new GuiButtonImage(937450782, guiLeft + xSize / 2 - 5 - 16, 115,  16, 16, xSize + 16, 0, 16, TEXTURE);
        this.buttonRight = new GuiButtonImage(937450783, guiLeft + xSize / 2 + 5, 115, 16, 16, xSize, 0, 16, TEXTURE);
        addButton(buttonLeft);
        addButton(buttonRight);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.disableDepth();
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GlStateManager.enableDepth();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String pageCount = page+"/"+3;
        drawStringCenter(pageCount, xSize/2, ySize/2+6, 0x404040);
    }

    private void drawTaskRequirements(ITask iTask) {
        switch (iTask.getType()) {
            case ITEM: {
                ItemTask task = (ItemTask) iTask;

            }
        }
    }

    protected void drawStringCenter(String string, int x, int y, int color) {
        mc.fontRenderer.drawString(string, x - mc.fontRenderer.getStringWidth(string) / 2f, y - mc.fontRenderer.FONT_HEIGHT / 2f, color, false);
    }

    public static class GuiPageQuest extends GuiQuest {

        public GuiPageQuest(EntityPlayer player, int entityID, int page) {
            super(player, entityID, page);
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
            if (villager != null) {
                drawStringCenter(villager.getDisplayName().getFormattedText(), xSize / 2, 10, 0x404040);
                IQuestHandler cap = villager.getCapability(CapabilityHandler.QUEST_CAPABILITY, null);
                if (cap != null) {
                    if (cap.getQuest() != null) {
                        IQuest quest = cap.getQuest();
                        drawStringCenter(TextFormatting.BOLD+quest.getQuestTitle(), xSize / 2, 25, 0x404040);
                        boolean lastUnicodeState = mc.fontRenderer.getUnicodeFlag();
                        mc.fontRenderer.setUnicodeFlag(true);
                        int nextHeight = 0;
                        for (int i = 0; i < quest.getQuestDescription().length; i++) {
                            String descPart = quest.getQuestDescription()[i];
                            if (i > 0) nextHeight = (i-1) * mc.fontRenderer.FONT_HEIGHT + mc.fontRenderer.getWordWrappedHeight(quest.getQuestDescription()[i-1], xSize - 20);
                            mc.fontRenderer.drawSplitString(descPart, 10, 30 + nextHeight, xSize - 20, 0x404040);
                        }

                        mc.fontRenderer.setUnicodeFlag(lastUnicodeState);
                    }
                    else {
                        drawStringCenter("None", xSize / 2, ySize / 2, 0x404040);
                    }
                }
            }
        }
    }
}