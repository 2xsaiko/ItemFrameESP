package therealfarfetchd.itemframeesp

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.settings.{KeyConflictContext, KeyModifier}
import org.lwjgl.input.Keyboard

object Keybinds {

  val ToggleBind = new KeyBinding("Toggle Item Frame ESP", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_I, ItemFrameESP.ModID)

}
