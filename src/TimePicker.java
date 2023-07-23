import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

public class TimePicker {

    Date selectedTime;

    public TimePicker(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Time Picker", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new BorderLayout());

        // Create the spinner
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        SpinnerDateModel model = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.MINUTE);
        JSpinner spinner = new JSpinner(model);

        JButton setButton = new JButton("Set Time");

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTime = (Date) spinner.getValue();
                dialog.dispose();
            }
        });

        // Create a panel and set its layout to center the components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10); // Add some padding

        panel.add(spinner, constraints);
        constraints.gridy = 1;
        panel.add(setButton, constraints);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public Date setPickedDate() {
        return selectedTime;
    }
}
