package therealfarfetchd.itemframeesp

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = ItemFrameESP.ModID, useMetadata = true, clientSideOnly = true, modLanguage = "scala")
object ItemFrameESP {
  final val ModID = "itemframeesp"

  var shouldDrawBoxes = false

  @EventHandler
  def preInit(e: FMLPreInitializationEvent): Unit = {
    MinecraftForge.EVENT_BUS.register(ClientProxy)
    ClientRegistry.registerKeyBinding(Keybinds.ToggleBind)
  }
}
