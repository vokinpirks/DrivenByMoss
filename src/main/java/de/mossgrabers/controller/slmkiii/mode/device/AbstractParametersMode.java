// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2020
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.slmkiii.mode.device;

import de.mossgrabers.controller.slmkiii.controller.SLMkIIIColorManager;
import de.mossgrabers.controller.slmkiii.controller.SLMkIIIControlSurface;
import de.mossgrabers.controller.slmkiii.controller.SLMkIIIDisplay;
import de.mossgrabers.controller.slmkiii.mode.BaseMode;
import de.mossgrabers.framework.controller.ButtonID;
import de.mossgrabers.framework.daw.IBank;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.data.IItem;
import de.mossgrabers.framework.mode.ModeManager;
import de.mossgrabers.framework.mode.Modes;


/**
 * Base Mode for editing device remote control parameters and user parameters.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public abstract class AbstractParametersMode extends BaseMode
{
    private static final String [] MODE_MENU =
    {
        "Devices",
        "Params",
        "",
        "",
        "",
        "",
        "",
        "User Prms"
    };


    /**
     * Constructor.
     *
     * @param name The name of the mode
     * @param surface The control surface
     * @param model The model
     * @param bank The parameter bank to control with this mode, might be null
     */
    public AbstractParametersMode (final String name, final SLMkIIIControlSurface surface, final IModel model, final IBank<? extends IItem> bank)
    {
        super (name, surface, model, bank);

        this.isTemporary = false;
    }


    protected void onButtonArrowUp (final int index)
    {
        final ModeManager modeManager = this.surface.getModeManager ();
        switch (index)
        {
            case 0:
                this.surface.getModeManager ().setActiveMode (Modes.DEVICE_PARAMS);
                ((ParametersMode) modeManager.getMode (Modes.DEVICE_PARAMS)).setShowDevices (true);
                break;

            case 1:
                this.surface.getModeManager ().setActiveMode (Modes.DEVICE_PARAMS);
                ((ParametersMode) modeManager.getMode (Modes.DEVICE_PARAMS)).setShowDevices (false);
                break;

            case 7:
                this.surface.getModeManager ().setActiveMode (Modes.USER);
                break;

            default:
                // Not used
                break;
        }

        this.surface.setTriggerConsumed (ButtonID.ARROW_UP);
    }


    protected int getButtonColorArrowUp (final ButtonID buttonID)
    {
        final ModeManager modeManager = this.surface.getModeManager ();
        final boolean isDeviceParams = modeManager.isActiveOrTempMode (Modes.DEVICE_PARAMS);
        switch (buttonID)
        {
            case ROW1_1:
                if (isDeviceParams && ((ParametersMode) modeManager.getMode (Modes.DEVICE_PARAMS)).isShowDevices ())
                    return SLMkIIIColorManager.SLMKIII_MINT;
                return SLMkIIIColorManager.SLMKIII_MINT_HALF;

            case ROW1_2:
                if (isDeviceParams && !((ParametersMode) modeManager.getMode (Modes.DEVICE_PARAMS)).isShowDevices ())
                    return SLMkIIIColorManager.SLMKIII_PURPLE;
                return SLMkIIIColorManager.SLMKIII_PURPLE_HALF;

            case ROW1_8:
                return modeManager.isActiveOrTempMode (Modes.USER) ? SLMkIIIColorManager.SLMKIII_WHITE : SLMkIIIColorManager.SLMKIII_WHITE_HALF;

            default:
                return SLMkIIIColorManager.SLMKIII_BLACK;
        }
    }


    /**
     * Draw the row with the mode selection.
     *
     * @param d The display
     */
    protected void drawRow4ArrowUp (final SLMkIIIDisplay d)
    {
        final ModeManager modeManager = this.surface.getModeManager ();
        for (int i = 0; i < 8; i++)
        {
            d.setCell (3, i, MODE_MENU[i]);

            boolean isActive = false;
            int color = SLMkIIIColorManager.SLMKIII_BLACK;

            final boolean isDeviceParams = modeManager.isActiveOrTempMode (Modes.DEVICE_PARAMS);
            switch (i)
            {
                case 0:
                    if (isDeviceParams && ((ParametersMode) modeManager.getMode (Modes.DEVICE_PARAMS)).isShowDevices ())
                        isActive = true;
                    color = SLMkIIIColorManager.SLMKIII_MINT;
                    break;

                case 1:
                    if (isDeviceParams && !((ParametersMode) modeManager.getMode (Modes.DEVICE_PARAMS)).isShowDevices ())
                        isActive = true;
                    color = SLMkIIIColorManager.SLMKIII_PURPLE;
                    break;

                case 7:
                    isActive = modeManager.isActiveOrTempMode (Modes.USER);
                    color = SLMkIIIColorManager.SLMKIII_WHITE;
                    break;

                default:
                    // Not used
                    break;
            }

            d.setPropertyColor (i, 2, color);
            d.setPropertyValue (i, 1, isActive ? 1 : 0);
        }
    }
}