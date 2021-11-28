package com.ricy40.caerula.entity.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class LulaEntity extends WaterMobEntity implements IAnimatable {

    public float xBodyRotO;
    private float tx;
    private float ty;
    private float tz;

    private AnimationFactory factory = new AnimationFactory(this);

    public LulaEntity(EntityType<? extends WaterMobEntity> type, World worldIn) {
        super(type, worldIn);
        this.random.setSeed((long) this.getId());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> animationEvent) {
        if (animationEvent.isMoving()) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula.swim", true));
            return PlayState.CONTINUE;
        } /* else if (!this.isInWater() && this.onGround && this.verticalCollision) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula.flop", true));
            return PlayState.CONTINUE;
        } */
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LulaEntity.MoveRandomGoal(this));
        this.goalSelector.addGoal(1, new LulaEntity.FleeGoal());
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D);
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.SQUID_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SQUID_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (super.hurt(source, damage) && this.getLastHurtByMob() != null) {
            this.spawnInk();
            return true;
        } else {
            return false;
        }
    }

    private Vector3d rotateVector(Vector3d vector) {
        Vector3d vector3d = vector.xRot(this.xBodyRotO * ((float)Math.PI / 180F));
        return vector3d.yRot(-this.yBodyRotO * ((float)Math.PI / 180F));
    }

    private void spawnInk() {
        this.playSound(SoundEvents.SQUID_SQUIRT, this.getSoundVolume(), this.getVoicePitch());
        Vector3d vector3d = this.rotateVector(new Vector3d(0.0D, -1.0D, 0.0D)).add(this.getX(), this.getY(), this.getZ());

        for(int i = 0; i < 30; ++i) {
            Vector3d vector3d1 = this.rotateVector(new Vector3d((double)this.random.nextFloat() * 0.6D - 0.3D, -1.0D, (double)this.random.nextFloat() * 0.6D - 0.3D));
            Vector3d vector3d2 = vector3d1.scale(0.3D + (double)(this.random.nextFloat() * 2.0F));
            ((ServerWorld)this.level).sendParticles(ParticleTypes.SQUID_INK, vector3d.x, vector3d.y + 0.1D, vector3d.z, 0, vector3d2.x, vector3d2.y, vector3d2.z, (double)0.1F);
        }
    }

    @Override
    public void travel(Vector3d vector) {
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    public static boolean checkLulaSpawnRules(EntityType<LulaEntity> entityIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 45 && pos.getY() < worldIn.getSeaLevel();
    }
    
    public void setMovementVector(float x, float y, float z) {
        this.tx = x;
        this.ty = y;
        this.tz = z;
    }

    public boolean hasMovementVector() {
        return this.tx != 0.0F || this.ty != 0.0F || this.tz != 0.0F;
    }

    class FleeGoal extends Goal {
        private int fleeTicks;

        private FleeGoal() {
        }

        @Override
        public boolean canUse() {
            LivingEntity livingentity = LulaEntity.this.getLastHurtByMob();
            if (LulaEntity.this.isInWater() && livingentity != null) {
                return LulaEntity.this.distanceToSqr(livingentity) < 100.0D;
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            this.fleeTicks = 0;
        }

        @Override
        public void tick() {
            ++this.fleeTicks;
            LivingEntity livingentity = LulaEntity.this.getLastHurtByMob();
            if (livingentity != null) {
                Vector3d vector3d = new Vector3d(LulaEntity.this.getX() - livingentity.getX(), LulaEntity.this.getY() - livingentity.getY(), LulaEntity.this.getZ() - livingentity.getZ());
                BlockState blockstate = LulaEntity.this.level.getBlockState(new BlockPos(LulaEntity.this.getX() + vector3d.x, LulaEntity.this.getY() + vector3d.y, LulaEntity.this.getZ() + vector3d.z));
                FluidState fluidstate = LulaEntity.this.level.getFluidState(new BlockPos(LulaEntity.this.getX() + vector3d.x, LulaEntity.this.getY() + vector3d.y, LulaEntity.this.getZ() + vector3d.z));
                if (fluidstate.is(FluidTags.WATER) || blockstate.isAir()) {
                    double d0 = vector3d.length();
                    if (d0 > 0.0D) {
                        vector3d.normalize();
                        float f = 3.0F;
                        if (d0 > 5.0D) {
                            f = (float)((double)f - (d0 - 5.0D) / 5.0D);
                        }

                        if (f > 0.0F) {
                            vector3d = vector3d.scale((double)f);
                        }
                    }

                    if (blockstate.isAir()) {
                        vector3d = vector3d.subtract(0.0D, vector3d.y, 0.0D);
                    }

                    LulaEntity.this.setMovementVector((float)vector3d.x / 20.0F, (float)vector3d.y / 20.0F, (float)vector3d.z / 20.0F);
                }

                if (this.fleeTicks % 10 == 5) {
                    LulaEntity.this.level.addParticle(ParticleTypes.BUBBLE, LulaEntity.this.getX(), LulaEntity.this.getY(), LulaEntity.this.getZ(), 0.0D, 0.0D, 0.0D);
                }

            }
        }
    }

    class MoveRandomGoal extends Goal {
        private final LulaEntity squid;

        public MoveRandomGoal(LulaEntity entityIn) {
            this.squid = entityIn;
        }

        public boolean canUse() {
            return true;
        }

        public void tick() {
            int i = this.squid.getNoActionTime();
            if (i > 100) {
                this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.squid.getRandom().nextInt(50) == 0 || !this.squid.wasTouchingWater || !this.squid.hasMovementVector()) {
                float f = this.squid.getRandom().nextFloat() * ((float)Math.PI * 2F);
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
                float f3 = MathHelper.sin(f) * 0.2F;
                this.squid.setMovementVector(f1, f2, f3);
            }

        }
    }

}
