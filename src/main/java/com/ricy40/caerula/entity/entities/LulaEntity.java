package com.ricy40.caerula.entity.entities;

import com.ricy40.caerula.block.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
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

import javax.annotation.Nullable;
import java.util.Random;

public class LulaEntity extends WaterMobEntity implements IAnimatable {

    private Vector3d direction = new Vector3d(0.0F, 0.0F, 0.0F);
    @Nullable
    private BlockPos coralPosition = null;
    private int coralCooldown = 0;
    private int timeWithCoral = 0;

    private AnimationFactory factory = new AnimationFactory(this);

    public LulaEntity(EntityType<? extends WaterMobEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new LulaEntity.MoveHelperController(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> animationEvent) {
        if (animationEvent.isMoving()) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula_entity.swim", true));
            return PlayState.CONTINUE;
        } else if (this.getLastHurtByMob() != null) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula_entity.hit", true));
            return PlayState.CONTINUE;
        }/*
        else if (!this.isInWater() && this.onGround && this.verticalCollision) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula.flop", true));
            return PlayState.CONTINUE;
        }*/

        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula_entity.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LulaEntity.CoralGoal(this));
        this.goalSelector.addGoal(1, new LulaEntity.SwimGoal(this));
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
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize size) {
        return size.height * 0.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
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
    public void aiStep() {
        super.aiStep();
    }

    protected boolean canRandomSwim() {
        return true;
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

    private Vector3d direction(Vector3d Vector) {
        Vector3d vector3d = Vector.xRot((float)this.direction.x * ((float)Math.PI / 180F));
        return vector3d.yRot(-this.yBodyRotO * ((float)Math.PI / 180F));
    }
    
    private void spawnInk() {
        this.playSound(SoundEvents.SQUID_SQUIRT, this.getSoundVolume(), this.getVoicePitch());
        Vector3d vector3d = this.direction(new Vector3d(0.0D, -1.0D, 0.0D)).add(this.getX(), this.getY(), this.getZ());

        for(int i = 0; i < 30; ++i) {
            Vector3d vector3d1 = this.direction;
            Vector3d vector3d2 = vector3d1.scale(0.3D + (double)(this.random.nextFloat() * 2.0F));
            ((ServerWorld)this.level).sendParticles(ParticleTypes.SQUID_INK, this.getX(), this.getY() + 0.1D, this.getZ(), 0, vector3d2.x, vector3d2.y, vector3d2.z, (double)0.1F);
        }
    }


    public void travel(Vector3d vector) {

        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(vector);
        }

    }

    @SuppressWarnings("Deprecation")
    public static boolean checkLulaSpawnRules(EntityType<LulaEntity> entityIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 45 && pos.getY() < worldIn.getSeaLevel();
    }

    static class MoveHelperController extends MovementController {
        private final LulaEntity lula;

        MoveHelperController(LulaEntity entityIn) {
            super(entityIn);
            this.lula = entityIn;
        }

        public void tick() {
            if (this.lula.isEyeInFluid(FluidTags.WATER)) {
                this.lula.setDeltaMovement(this.lula.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MovementController.Action.MOVE_TO && !this.lula.getNavigation().isDone()) {
                float f = (float) (this.speedModifier * this.lula.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.lula.setSpeed(MathHelper.lerp(0.125F, this.lula.getSpeed(), f));
                double d0 = this.wantedX - this.lula.getX();
                double d1 = this.wantedY - this.lula.getY();
                double d2 = this.wantedZ - this.lula.getZ();
                if (d1 != 0.0D) {
                    double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.lula.setDeltaMovement(this.lula.getDeltaMovement().add(0.0D, (double) this.lula.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if (d0 != 0.0D || d2 != 0.0D) {
                    float f1 = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                    this.lula.yRot = this.rotlerp(this.lula.yRot, f1, 90.0F);
                    this.lula.yBodyRot = this.lula.yRot;
                }

            } else {
                this.lula.setSpeed(0.0F);
            }
        }
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final LulaEntity lula;

        public SwimGoal(LulaEntity entityIn) {
            super(entityIn, 1.0D, 40);
            this.lula = entityIn;
        }

        public boolean canUse() {
            return this.lula.canRandomSwim() && super.canUse();
        }

    }

    private class CoralGoal extends RandomSwimmingGoal {
        private final LulaEntity lula;

        CoralGoal(LulaEntity entityIn) {
            super(entityIn, 1.0D, 40);
            this.lula = entityIn;
        }

        public void tick() {

            if (this.lula.coralPosition != null && this.lula.coralCooldown == 0) {
                this.lula.navigation.moveTo(this.lula.navigation.createPath(coralPosition, 6), 1);

            } else if (this.lula.timeWithCoral != 0) {

                if (this.lula.timeWithCoral == 360) {
                    this.lula.coralCooldown = 0;
                    this.lula.coralPosition = null;
                    this.lula.timeWithCoral = 0;
                }

                this.lula.timeWithCoral++;

            } else if (this.lula.coralCooldown == 360) {

                BlockPos pos = new BlockPos(this.lula.getPosition(0));

                for (BlockPos blockpos : BlockPos.withinManhattan(pos, 10, 10, 10)) {

                    BlockState blockstate = this.lula.level.getBlockState(blockpos);
                    Block block = blockstate.getBlock();

                    if (block == BlockInit.BUSH_CORAL.get()) {
                        this.lula.coralPosition = blockpos;
                        System.out.println("Block found!");
                    }
                }
            } else {
                this.lula.coralCooldown++;
            }
        }
    }
}
