module lk.ijse.gdse71.dreamlandkids {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires java.sql;
    requires java.desktop;
    requires lombok;
    requires org.apache.poi.ooxml;

//requires com.lowagie.itext;  // For PDF export (iText library)
//    requires org.apache.poi;     // For Excel export (Apache POI)
//    requires org.apache.poi.ooxml;
    opens lk.ijse.gdse71.dreamlandkids.dto.tm to javafx.base;
    opens lk.ijse.gdse71.dreamlandkids.controller to javafx.fxml;
    exports lk.ijse.gdse71.dreamlandkids;
}