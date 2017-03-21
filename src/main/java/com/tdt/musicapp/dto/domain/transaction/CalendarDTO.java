package com.tdt.musicapp.dto.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDTO {
    protected Integer year;
    protected Integer month;
    protected Integer dayOfMonth;
    protected Integer hourOfDay;
    protected Integer minute;
    protected Integer second;

    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm Z");
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year, this.month, this.dayOfMonth, this.hourOfDay, this.minute, this.second);
        String date = sdf.format(calendar.getTime());
        System.out.println(date);
        return date;

    }
}
