package net.minecraft.server;

public class ContainerBeacon extends Container {

    private TileEntityBeacon a;
    private final SlotBeacon f;
    private int g;
    private int h;
    private int i;

    public ContainerBeacon(PlayerInventory playerinventory, TileEntityBeacon tileentitybeacon) {
        this.a = tileentitybeacon;
        this.a(this.f = new SlotBeacon(this, tileentitybeacon, 0, 136, 110));
        byte b0 = 36;
        short short1 = 137;

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + i * 9 + 9, b0 + j * 18, short1 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, b0 + i * 18, 58 + short1));
        }

        this.g = tileentitybeacon.l();
        this.h = tileentitybeacon.j();
        this.i = tileentitybeacon.k();
    }

    public void addSlotListener(ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.g);
        icrafting.setContainerData(this, 1, this.h);
        icrafting.setContainerData(this, 2, this.i);
    }

    public void b() {
        super.b();
    }

    public TileEntityBeacon e() {
        return this.a;
    }

    public boolean a(EntityHuman entityhuman) {
        return this.a.a(entityhuman);
    }

    public ItemStack b(EntityHuman entityhuman, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.c.get(i);

        if (slot != null && slot.d()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.cloneItemStack();
            if (i == 0) {
                if (!this.a(itemstack1, 1, 37, true)) {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            } else if (!this.f.d() && this.f.isAllowed(itemstack1) && itemstack1.count == 1) {
                if (!this.a(itemstack1, 0, 1, false)) {
                    return null;
                }
            } else if (i >= 1 && i < 28) {
                if (!this.a(itemstack1, 28, 37, false)) {
                    return null;
                }
            } else if (i >= 28 && i < 37) {
                if (!this.a(itemstack1, 1, 28, false)) {
                    return null;
                }
            } else if (!this.a(itemstack1, 1, 37, false)) {
                return null;
            }

            if (itemstack1.count == 0) {
                slot.set((ItemStack) null);
            } else {
                slot.e();
            }

            if (itemstack1.count == itemstack.count) {
                return null;
            }

            slot.a(entityhuman, itemstack1);
        }

        return itemstack;
    }
}
