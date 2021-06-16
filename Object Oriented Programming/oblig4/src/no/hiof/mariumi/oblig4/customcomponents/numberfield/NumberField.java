package no.hiof.mariumi.oblig4.customcomponents.numberfield;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberField extends TextField {
    public NumberField() {
        super();
        restrictToNumbersOnly();
    }

    public NumberField(String text) {
        super(text);
        restrictToNumbersOnly();
    }

    public void restrictToNumbersOnly() {
        lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char x = getText().charAt(oldValue.intValue());
                    if (!(x >= '0' && x <= '9')) {
                        setText(getText().substring(0, getText().length() - 1));
                    }
                }
            }
        });
    }
}
