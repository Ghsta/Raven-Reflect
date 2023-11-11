package zen.relife.module.impl.example;


import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.setting.ModeSetting;

import javax.swing.*;
import java.util.Arrays;

public class example extends Module {

    public IntegerSetting test1;
    public EnableSetting test2;
    public ModeSetting test3;

    public example() {

        super("Disabler", Keyboard.KEY_NONE, Category.EXPLOIT, false);
        //IntegerSetting 使用方法
        //current 当前值 ； min ； 最小值 ； max 最大值 ； dou 小数点后的位数
        this.test1 = new IntegerSetting("test1", 4.3, 1.0, 10.0, 1);

        //EnableSetting 使用方法
        this.test2 = new EnableSetting("test2", true);

        //ModeSetting 使用方法                      //current 当前值 Arrays.asList : mode名称   //
        //if (!setting.getName().equalsIgnoreCase(name) || !((ModeSetting) setting).getParent().getName().equalsIgnoreCase(mod))
        //   continue;
        this.test3 = new ModeSetting("test3", "1", Arrays.asList("1", "2"), this);
        //Module hitBox = Relife.INSTANCE.getModuleManager().getModule("HitBox");
        //return hitBox != null && this.getState() ? (double) this.getInput : 1.0;
    }

    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) { //@SubscribeEvent注解,它用来标记这是一个订阅器，至于它具体监听的事件是由它的参数类型决定的，在这里它的参数类型是EntityItemPickupEvent，说明它监听的是实体捡起物品这个事件
        JOptionPane.showMessageDialog(null, "你拾起了一个东西");
        System.out.println("你拾起了一个东西");
    }
}

