package therealfarfetchd.itemframeesp

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager._
import net.minecraft.entity.item.EntityItemFrame
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import org.lwjgl.opengl.GL11.{GL_LINES, GL_LINE_STRIP, glVertex3d}

import scala.collection.JavaConversions._

object ClientProxy {
  @SubscribeEvent
  def onRenderEntity(e: RenderWorldLastEvent): Unit = {
    if (!ItemFrameESP.shouldDrawBoxes) return

    val mc = Minecraft.getMinecraft
    val world = mc.world
    val player = mc.player
    val pt = e.getPartialTicks

    pushMatrix()

    translate(
      -interp(player.prevPosX, player.posX, pt),
      -interp(player.prevPosY, player.posY, pt),
      -interp(player.prevPosZ, player.posZ, pt)
    )

    disableDepth()
    depthMask(false)
    enableBlend()
    disableLighting()
    disableTexture2D()
    blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA)

    world.loadedEntityList
      .filter(_.isInstanceOf[EntityItemFrame])
      .map(_.asInstanceOf[EntityItemFrame])
      .foreach(frame => {
        pushMatrix()
        val bb = frame.getEntityBoundingBox
        glLineWidth(2.0f)
        color(0f, 1f, 0f, 0.75f)

        glBegin(GL_LINE_STRIP)
        glVertex3d(bb.minX, bb.minY, bb.minZ)
        glVertex3d(bb.maxX, bb.minY, bb.minZ)
        glVertex3d(bb.maxX, bb.minY, bb.maxZ)
        glVertex3d(bb.minX, bb.minY, bb.maxZ)
        glVertex3d(bb.minX, bb.minY, bb.minZ)
        glVertex3d(bb.minX, bb.maxY, bb.minZ)
        glVertex3d(bb.maxX, bb.maxY, bb.minZ)
        glVertex3d(bb.maxX, bb.maxY, bb.maxZ)
        glVertex3d(bb.minX, bb.maxY, bb.maxZ)
        glVertex3d(bb.minX, bb.maxY, bb.minZ)
        glEnd()
        glBegin(GL_LINES)
        glVertex3d(bb.maxX, bb.minY, bb.minZ)
        glVertex3d(bb.maxX, bb.maxY, bb.minZ)
        glVertex3d(bb.maxX, bb.minY, bb.maxZ)
        glVertex3d(bb.maxX, bb.maxY, bb.maxZ)
        glVertex3d(bb.minX, bb.minY, bb.maxZ)
        glVertex3d(bb.minX, bb.maxY, bb.maxZ)
        glEnd()

        popMatrix()
      })

    depthMask(true)
    enableLighting()
    enableDepth()
    enableTexture2D()
    popMatrix()
  }

  @SubscribeEvent
  def onKeyPressed(e: KeyInputEvent): Unit = {
    if (Keybinds.ToggleBind.isPressed) {
      ItemFrameESP.shouldDrawBoxes = !ItemFrameESP.shouldDrawBoxes
    }
  }

  def interp(v1: Double, v2: Double, p: Double): Double = v1 + (v2 - v1) * p
}
