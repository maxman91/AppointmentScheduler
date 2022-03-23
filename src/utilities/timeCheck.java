package utilities;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

import static model.appointment.getAllAppointments;
/** timeCheck class checks for scheduling conflicts for customers appointments.*/
public class timeCheck {
    /** checkScheduleConflict checks if their is a scheduling conflict when user attempts to input data into database.
      @param C the customer id.
      @param E  the end of the appointment time.
      @param S  The start of the appointment time.
     @return returns true if their is a conflict.
      */
    public static boolean checkScheduleConflict(LocalDateTime S,LocalDateTime E, int C){
        AtomicBoolean hasConflict = new AtomicBoolean(false);
        getAllAppointments().forEach((n) -> {
            LocalDateTime SA = n.getStart().toLocalDateTime();
            LocalDateTime EA = n.getEnd().toLocalDateTime();
            //System.out.println(SA +"||"+EA);
            if (n.getCustomerID() == C) {
                if ((S.isBefore(EA)  || S.isEqual(SA)) && (S.isAfter(SA))){

                    hasConflict.set(true);

                }


                if ((E.isBefore(EA) || (E.isEqual(SA))) && (E.isAfter(SA))){
                    hasConflict.set(true);
                }
                if ((S.isBefore(SA) || S.isEqual(SA)) && (E.isAfter(EA) || E.isEqual(EA))){
                    hasConflict.set(true);
                }


            }


        });






        return hasConflict.get();
    }
    /** checkScheduleConflictUpdate checks if their is a scheduling conflict when user attempts to update appointment.
     @param C the customer id.
     @param E  the end of the appointment time.
     @param S  The start of the appointment time.
     @param AppointmentID appointment id.
     @return returns true if their is a conflict.

     */
    public static boolean checkScheduleConflictUpdate(int AppointmentID, LocalDateTime S,LocalDateTime E, int C){
        AtomicBoolean hasConflict = new AtomicBoolean(false);
        getAllAppointments().forEach((n) -> {
            LocalDateTime SA = n.getStart().toLocalDateTime();
            LocalDateTime EA = n.getEnd().toLocalDateTime();
            //System.out.println(SA +"||"+EA);
            if (n.getCustomerID() == C && AppointmentID != n.getAppointmentID()) {
                if ((S.isBefore(EA)  || S.isEqual(SA)) && (S.isAfter(SA))){

                    hasConflict.set(true);

                }


                if ((E.isBefore(EA) || (E.isEqual(SA))) && (E.isAfter(SA))){
                    hasConflict.set(true);
                }
                if ((S.isBefore(SA) || S.isEqual(SA)) && (E.isAfter(EA) || E.isEqual(EA))){
                    hasConflict.set(true);
                }


            }


        });






        return hasConflict.get();
    }

}
