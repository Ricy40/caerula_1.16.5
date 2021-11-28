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
import net.minecraft.potion.Effects;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class LulaEntity extends WaterMobEntity implements IAnimatable {

    public float xBodyRot;
    public float xBodyRotO;
    public float zBodyRot;
    public float zBodyRotO;
    public float tentacleMovement;
    public float oldTentacleMovement;
    public float tentacleAngle;
    public float oldTentacleAngle;
    private float speed;
    private float tentacleSpeed;
    private float rotateSpeed;
    private float tx;
    private float ty;
    private float tz;

    private AnimationFactory factory = new AnimationFactory(this);

    public LulaEntity(EntityType<? extends WaterMobEntity> type, World worldIn) {
        super(type, worldIn);
        this.random.setSeed((long) this.getId());
        this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
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
        }
        /* else if (!this.isInWater() && this.onGround && this.verticalCollision) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula.flop", true));
            return PlayState.CONTINUE;
        } */

        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lula_entity.idle", true));
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

    public void aiStep() {
        super.aiStep();
        this.xBodyRotO = this.xBodyRot;
        this.zBodyRotO = this.zBodyRot;
        this.oldTentacleMovement = this.tentacleMovement;
        this.oldTentacleAngle = this.tentacleAngle;
        this.tentacleMovement += this.tentacleSpeed;
        if ((double)this.tentacleMovement > (Math.PI * 2D)) {
            if (this.level.isClientSide) {
                this.tentacleMovement = ((float)Math.PI * 2F);
            } else {
                this.tentacleMovement = (float)((double)this.tentacleMovement - (Math.PI * 2D));
                if (this.random.nextInt(10) == 0) {
                    this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.level.broadcastEntityEvent(this, (byte)19);
            }
        }

        if (this.isInWaterOrBubble()) {
            if (this.tentacleMovement < (float)Math.PI) {
                float f = this.tentacleMovement / (float)Math.PI;
                this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
                if ((double)f > 0.75D) {
                    this.speed = 1.0F;
                    this.rotateSpeed = 1.0F;
                } else {
                    this.rotateSpeed *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.speed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }

            if (!this.level.isClientSide) {
                this.setDeltaMovement((double)(this.tx * this.speed), (double)(this.ty * this.speed), (double)(this.tz * this.speed));
            }

            Vector3d vector3d = this.getDeltaMovement();
            float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
            this.yBodyRot += (-((float)MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI) - this.yBodyRot) * 0.1F;
            this.yRot = this.yBodyRot;
            this.zBodyRot = (float)((double)this.zBodyRot + Math.PI * (double)this.rotateSpeed * 1.5D);
            this.xBodyRot += (-((float)MathHelper.atan2((double)f1, vector3d.y)) * (180F / (float)Math.PI) - this.xBodyRot) * 0.1F;
        } else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.tentacleMovement)) * (float)Math.PI * 0.25F;
            if (!this.level.isClientSide) {
                double d0 = this.getDeltaMovement().y;
                if (this.hasEffect(Effects.LEVITATION)) {
                    d0 = 0.05D * (double)(this.getEffect(Effects.LEVITATION).getAmplifier() + 1);
                } else if (!this.isNoGravity()) {
                    d0 -= 0.08D;
                }

                this.setDeltaMovement(0.0D, d0 * (double)0.98F, 0.0D);
            }

            this.xBodyRot = (float)((double)this.xBodyRot + (double)(-90.0F - this.xBodyRot) * 0.02D);
        }

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

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte bite) {
        if (bite == 19) {
            this.tentacleMovement = 0.0F;
        } else {
            super.handleEntityEvent(bite);
        }

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
