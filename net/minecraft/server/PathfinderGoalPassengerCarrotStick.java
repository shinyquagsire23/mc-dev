package net.minecraft.server;

public class PathfinderGoalPassengerCarrotStick extends PathfinderGoal {

    private final EntityLiving a;
    private final float b;
    private float c = 0.0F;
    private boolean d = false;
    private int e = 0;
    private int f = 0;

    public PathfinderGoalPassengerCarrotStick(EntityLiving entityliving, float f) {
        this.a = entityliving;
        this.b = f;
        this.a(7);
    }

    public void c() {
        this.c = 0.0F;
    }

    public void d() {
        this.d = false;
        this.c = 0.0F;
    }

    public boolean a() {
        return this.a.isAlive() && this.a.passenger != null && this.a.passenger instanceof EntityHuman && (this.d || this.a.bL());
    }

    public void e() {
        EntityHuman entityhuman = (EntityHuman) this.a.passenger;
        EntityCreature entitycreature = (EntityCreature) this.a;
        float f = MathHelper.g(entityhuman.yaw - this.a.yaw) * 0.5F;

        if (f > 5.0F) {
            f = 5.0F;
        }

        if (f < -5.0F) {
            f = -5.0F;
        }

        this.a.yaw = MathHelper.g(this.a.yaw + f);
        if (this.c < this.b) {
            this.c += (this.b - this.c) * 0.01F;
        }

        if (this.c > this.b) {
            this.c = this.b;
        }

        int i = MathHelper.floor(this.a.locX);
        int j = MathHelper.floor(this.a.locY);
        int k = MathHelper.floor(this.a.locZ);
        float f1 = this.c;

        if (this.d) {
            if (this.e++ > this.f) {
                this.d = false;
            }

            f1 += f1 * 1.15F * MathHelper.sin((float) this.e / (float) this.f * 3.1415927F);
        }

        float f2 = 0.91F;

        if (this.a.onGround) {
            f2 = 0.54600006F;
            int l = this.a.world.getTypeId(MathHelper.d((float) i), MathHelper.d((float) j) - 1, MathHelper.d((float) k));

            if (l > 0) {
                f2 = Block.byId[l].frictionFactor * 0.91F;
            }
        }

        float f3 = 0.16277136F / (f2 * f2 * f2);
        float f4 = MathHelper.sin(entitycreature.yaw * 3.1415927F / 180.0F);
        float f5 = MathHelper.cos(entitycreature.yaw * 3.1415927F / 180.0F);
        float f6 = entitycreature.aI() * f3;
        float f7 = Math.max(f1, 1.0F);

        f7 = f6 / f7;
        float f8 = f1 * f7;
        float f9 = -(f8 * f4);
        float f10 = f8 * f5;

        if (MathHelper.abs(f9) > MathHelper.abs(f10)) {
            if (f9 < 0.0F) {
                f9 -= this.a.width / 2.0F;
            }

            if (f9 > 0.0F) {
                f9 += this.a.width / 2.0F;
            }

            f10 = 0.0F;
        } else {
            f9 = 0.0F;
            if (f10 < 0.0F) {
                f10 -= this.a.width / 2.0F;
            }

            if (f10 > 0.0F) {
                f10 += this.a.width / 2.0F;
            }
        }

        int i1 = MathHelper.floor(this.a.locX + (double) f9);
        int j1 = MathHelper.floor(this.a.locZ + (double) f10);
        PathPoint pathpoint = new PathPoint(MathHelper.d(this.a.width + 1.0F), MathHelper.d(this.a.length + entityhuman.length + 1.0F), MathHelper.d(this.a.width + 1.0F));

        if (i != i1 || k != j1) {
            int k1 = this.a.world.getTypeId(i, j, k);
            int l1 = this.a.world.getTypeId(i, j - 1, k);
            boolean flag = this.b(k1) || Block.byId[k1] == null && this.b(l1);

            if (!flag && Pathfinder.a(this.a, i1, j, j1, pathpoint, false, false, true) == 0 && Pathfinder.a(this.a, i, j + 1, k, pathpoint, false, false, true) == 1 && Pathfinder.a(this.a, i1, j + 1, j1, pathpoint, false, false, true) == 1) {
                entitycreature.getControllerJump().a();
            }
        }

        if (!entityhuman.abilities.canInstantlyBuild && this.c >= this.b * 0.5F && this.a.aE().nextFloat() < 0.006F && !this.d) {
            ItemStack itemstack = entityhuman.bG();

            if (itemstack != null && itemstack.id == Item.CARROT_STICK.id) {
                itemstack.damage(1, entityhuman);
                if (itemstack.count == 0) {
                    ItemStack itemstack1 = new ItemStack(Item.FISHING_ROD);

                    itemstack1.setTag(itemstack.tag);
                    entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack1;
                }
            }
        }

        this.a.e(0.0F, f1);
    }

    private boolean b(int i) {
        return Block.byId[i] != null && (Block.byId[i].d() == 10 || Block.byId[i] instanceof BlockStepAbstract);
    }

    public boolean f() {
        return this.d;
    }

    public void g() {
        this.d = true;
        this.e = 0;
        this.f = this.a.aE().nextInt(841) + 140;
    }

    public boolean h() {
        return !this.f() && this.c > this.b * 0.3F;
    }
}
