package com.todo.domain;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "TODOS")
public class Todos extends BaseEntity {

    private static final String DATE_FORMATTER_WITH_DASH = "dd/MM/yyyy";
    private static final DateFormat FORMAT = new SimpleDateFormat(DATE_FORMATTER_WITH_DASH);

    @Column(length = 255, nullable = false)
    private String explanation;

    private Date startDate = Calendar.getInstance().getTime();

    private Date endDate = Calendar.getInstance().getTime();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users user;

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getFormattedEndDate() {
        return FORMAT.format(endDate);
    }
    public String getFormattedStartDate() {
        return FORMAT.format(startDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
