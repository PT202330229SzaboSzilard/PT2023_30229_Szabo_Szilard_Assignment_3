package ro.tuc.start;

import ro.tuc.gui.View;

import javax.swing.*;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Start {

    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {

        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               new View();
           }
        });

    }

}
