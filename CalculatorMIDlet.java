/*
 
 * and one or more additional patents or pending patent applications
 * in the U.S. and in other countries.
 *
 * Unpublished - rights reserved under the Copyright Laws of the
 * United States.
 *
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION AND TRADE SECRETS
 * OF SUN MICROSYSTEMS, INC. USE, DISCLOSURE OR REPRODUCTION IS
 * PROHIBITED WITHOUT THE PRIOR EXPRESS WRITTEN PERMISSION OF SUN
 * MICROSYSTEMS, INC.
 *
 * U.S. Government Rights - Commercial software. Government users are
 * subject to the Sun Microsystems, Inc.
 * standard license agreement and applicable provisions of the FAR and
 * its supplements.Use is subject to license terms.
 * This distribution may include materials developed by third parties.
 *
 * Sun, Sun Microsystems, the Sun logo, Java, Jini and the Java Coffee
 * Cup logo are trademarks or registered trademarks of Sun
 * Microsystems, Inc. in the U.S. and other countries.
 *
 * This product is covered and controlled by U.S. Export Control laws
 * and may be subject to the export or import laws in other countries.
 * Nuclear, missile, chemical biological weapons or nuclear maritime
 * end uses or end users, whether direct or indirect, are strictly
 * prohibited. Export or reexport to countries subject to U.S. embargo
 * or to entities identified on U.S. export exclusion lists, including,
 * but not limited to the denied persons and specially designated
 * nationals lists is strictly prohibited.
 */

package calculator;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
//  import java.util.Arrays;   check paths etc - mike 30 June 2015

/**
 * The calculator demo is a simple floating point calculator
 * which powered by floating point support available in cldc1.1.
 *
 * @version
 */
public final class CalculatorMIDlet extends MIDlet implements CommandListener {
    /** The number of characters in numeric text field. */
    private static final int NUM_SIZE = 20;

    /** Soft button for exiting the game. */
    private final Command exitCmd = new Command("Exit", Command.EXIT, 2);

    /** Menu item for changing game levels. */
    private final Command calcCmd = new Command("Calc", Command.SCREEN, 1);

    /** A text field to keep the first argument. */
    private final TextField t1 = new TextField(null, "", NUM_SIZE, TextField.DECIMAL);

    /** A text field to keep the second argument. */
    private final TextField t2 = new TextField(null, "", NUM_SIZE, TextField.DECIMAL);

    /** A text field to keep the result of calculation. */
    private final TextField tr = new TextField("Result", "", NUM_SIZE, TextField.UNEDITABLE);

    /** A choice group with available operations. */
    private final ChoiceGroup cg =
        new ChoiceGroup("", ChoiceGroup.POPUP,
            new String[] { "add", "subtract", "multiply", "divide" }, null);

    /** An alert to be reused for different errors. */
    private final Alert alert = new Alert("Error", "", null, AlertType.ERROR);

    /** Indicates if the application is initialized. */
    private boolean isInitialized = false;

    /**
     * Creates the calculator view and action buttons.
     */
    protected void startApp() {
        if (isInitialized) {
            return;
        }

        Form f = new Form("FP Mike J8 Calculator");

        // adding a lambda expression
        new Thread(() -> System.out.println("Hi there Mike")).start();
        f.append(t1);
        f.append(cg);
        f.append(t2);
        f.append(tr);
        f.addCommand(exitCmd);
        f.addCommand(calcCmd);
        f.setCommandListener(this);
        Display.getDisplay(this).setCurrent(f);
        alert.addCommand(new Command("Back", Command.SCREEN, 1));
        isInitialized = true;
    }

    /**
     * Does nothing. Redefinition is required by MIDlet class.
     */
    protected void destroyApp(boolean unconditional) {
    }

    /**
     * Does nothing. Redefinition is required by MIDlet class.
     */
    protected void pauseApp() {
    }

    /**
     * Responds to commands issued on CalculatorForm.
     *
     * @param c command object source of action
     * @param d screen object containing the item the action was performed on.
     */
    public void commandAction(Command c, Displayable d) {
        if (c == exitCmd) {
            destroyApp(false);
            notifyDestroyed();

            return;
        }

        double res = 0.0;

        try {
            double n1 = getNumber(t1, "First");
            double n2 = getNumber(t2, "Second");

            switch (cg.getSelectedIndex()) {
            case 0:
                res = n1 + n2;

                break;

            case 1:
                res = n1 - n2;

                break;

            case 2:
                res = n1 * n2;

                break;

            case 3:
                res = n1 / n2;

                break;

            default:
            }
        } catch (NumberFormatException e) {
            return;
        } catch (ArithmeticException e) {
            alert.setString("Divide by zero.");
            Display.getDisplay(this).setCurrent(alert);

            return;
        }

        /*
         * The resulted string may exceed the text max size.
         * We need to correct last one then.
         */
        String res_str = Double.toString(res);

        if (res_str.length() > tr.getMaxSize()) {
            tr.setMaxSize(res_str.length());
        }

        tr.setString(res_str);
    }

    /**
     * Extracts the double number from text field.
     *
     * @param t the text field to be used.
     * @param type the string with argument number.
     * @throws NumberFormatException is case of wrong input.
     */
    private double getNumber(TextField t, String type)
        throws NumberFormatException {
        String s = t.getString();

        if (s.length() == 0) {
            alert.setString("No " + type + " Argument");
            Display.getDisplay(this).setCurrent(alert);
            throw new NumberFormatException();
        }

        double n;

        try {
            n = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            alert.setString(type + " argument is out of range.");
            Display.getDisplay(this).setCurrent(alert);
            throw e;
        }

        return n;
    }
} // end of class 'CalculatorMIDlet' definition
