package com.deathspawn.advanced.capabilityhandlers;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class DynamicFluidTank extends FluidTank
{
    @Nullable
    protected FluidStack fluid;
    protected int capacity;
    protected TileEntity tile;
    protected boolean canFill = true;
    protected boolean canDrain = true;
    protected IFluidTankProperties[] tankProperties;

    public DynamicFluidTank(int capacity)
    {
        super(null, capacity);
    }

    public DynamicFluidTank(Fluid fluid, int amount, int capacity)
    {
        super(new FluidStack(fluid, amount), capacity);
    }

    public DynamicFluidTank readFromNBT(NBTTagCompound nbt)
    {
        if (!nbt.hasKey("Empty"))
        {
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
            setFluid(fluid);
        }
        else
        {
            setFluid(null);
        }
        this.capacity = nbt.getInteger("tankCapacity");
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        if (fluid != null)
        {
            fluid.writeToNBT(nbt);
        }
        else
        {
            nbt.setString("Empty", "");
        }
        nbt.setInteger("tankCapacity", this.capacity);
        return nbt;
    }

    /* IFluidTank */
    @Override
    @Nullable
    public FluidStack getFluid()
    {
        return fluid;
    }

    public void setFluid(@Nullable FluidStack fluid)
    {
        this.fluid = fluid;
    }

    @Override
    public int getFluidAmount()
    {
        if (fluid == null)
        {
            return 0;
        }
        return fluid.amount;
    }

    @Override
    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public void setTileEntity(TileEntity tile)
    {
        this.tile = tile;
    }

    @Override
    public FluidTankInfo getInfo()
    {
        return new FluidTankInfo(this);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        if (!canFillFluidType(resource))
        {
            return 0;
        }

        return fillInternal(resource, doFill);
    }

    /**
     * Use this method to bypass the restrictions from {@link #canFillFluidType(FluidStack)}
     * Meant for use by the owner of the tank when they have {@link #canFill() set to false}.
     */
    public int fillInternal(FluidStack resource, boolean doFill)
    {
        if (resource == null || resource.amount <= 0)
        {
            return 0;
        }

        if (!doFill)
        {
            if (fluid == null)
            {
                return Math.min(capacity, resource.amount);
            }

            if (!fluid.isFluidEqual(resource))
            {
                return 0;
            }

            return Math.min(capacity - fluid.amount, resource.amount);
        }

        if (fluid == null)
        {
            fluid = new FluidStack(resource, Math.min(capacity, resource.amount));

            onContentsChanged();

            if (tile != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid, tile.getWorld(), tile.getPos(), this, fluid.amount));
            }
            return fluid.amount;
        }

        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }
        int filled = capacity - fluid.amount;

        if (resource.amount < filled)
        {
            fluid.amount += resource.amount;
            filled = resource.amount;
        }
        else
        {
            fluid.amount = capacity;
        }

        onContentsChanged();

        if (tile != null)
        {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid, tile.getWorld(), tile.getPos(), this, filled));
        }
        return filled;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
        if (!canDrainFluidType(getFluid()))
        {
            return null;
        }
        return drainInternal(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        if (!canDrainFluidType(fluid))
        {
            return null;
        }
        return drainInternal(maxDrain, doDrain);
    }

    /**
     * Use this method to bypass the restrictions from {@link #canDrainFluidType(FluidStack)}
     * Meant for use by the owner of the tank when they have {@link #canDrain()} set to false}.
     */
    @Nullable
    public FluidStack drainInternal(FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(getFluid()))
        {
            return null;
        }
        return drainInternal(resource.amount, doDrain);
    }

    /**
     * Use this method to bypass the restrictions from {@link #canDrainFluidType(FluidStack)}
     * Meant for use by the owner of the tank when they have {@link #canDrain()} set to false}.
     */
    @Nullable
    public FluidStack drainInternal(int maxDrain, boolean doDrain)
    {
        if (fluid == null || maxDrain <= 0)
        {
            return null;
        }

        int drained = maxDrain;
        if (fluid.amount < drained)
        {
            drained = fluid.amount;
        }

        FluidStack stack = new FluidStack(fluid, drained);
        if (doDrain)
        {
            fluid.amount -= drained;
            if (fluid.amount <= 0)
            {
                fluid = null;
            }

            onContentsChanged();

            if (tile != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluid, tile.getWorld(), tile.getPos(), this, drained));
            }
        }
        return stack;
    }

    /**
     * Whether this tank can be filled with {@link IFluidHandler}
     *
     * @see IFluidTankProperties#canFill()
     */
    public boolean canFill()
    {
        return canFill;
    }

    /**
     * Whether this tank can be drained with {@link IFluidHandler}
     *
     * @see IFluidTankProperties#canDrain()
     */
    public boolean canDrain()
    {
        return canDrain;
    }

    /**
     * Set whether this tank can be filled with {@link IFluidHandler}
     *
     * @see IFluidTankProperties#canFill()
     */
    public void setCanFill(boolean canFill)
    {
        this.canFill = canFill;
    }

    /**
     * Set whether this tank can be drained with {@link IFluidHandler}
     *
     * @see IFluidTankProperties#canDrain()
     */
    public void setCanDrain(boolean canDrain)
    {
        this.canDrain = canDrain;
    }

    /**
     * Returns true if the tank can be filled with this type of fluid.
     * Used as a filter for fluid types.
     * Does not consider the current contents or capacity of the tank,
     * only whether it could ever fill with this type of fluid.
     *
     * @see IFluidTankProperties#canFillFluidType(FluidStack)
     */
    public boolean canFillFluidType(FluidStack fluid)
    {
        return canFill();
    }

    /**
     * Returns true if the tank can drain out this type of fluid.
     * Used as a filter for fluid types.
     * Does not consider the current contents or capacity of the tank,
     * only whether it could ever drain out this type of fluid.
     *
     * @see IFluidTankProperties#canDrainFluidType(FluidStack)
     */
    public boolean canDrainFluidType(@Nullable FluidStack fluid)
    {
        return fluid != null && canDrain();
    }

    protected void onContentsChanged()
    {

    }
}
