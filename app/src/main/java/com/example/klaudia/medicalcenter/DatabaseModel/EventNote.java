package com.example.klaudia.medicalcenter.DatabaseModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class EventNote extends EventDay implements Parcelable {
    private String mNote;

    public EventNote(Calendar day, int imageResource, String note) {
        super(day, imageResource);
        mNote = note;
    }

    public String getNote() {
        return mNote;
    }

    private EventNote(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mNote = in.readString();
    }

    public static final Creator<EventNote> CREATOR = new Creator<EventNote>() {
        @Override
        public EventNote createFromParcel(Parcel in) {
            return new EventNote(in);
        }
        @Override
        public EventNote[] newArray(int size) {
            return new EventNote[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(i);
        parcel.writeString(mNote);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}