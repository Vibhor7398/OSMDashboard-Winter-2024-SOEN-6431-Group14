package de.storchp.opentracks.osmplugin.utils;

public class TrackPointsDebug {
    public int trackpointsReceived = 0;
    public int trackpointsInvalid = 0;
    private int trackpointsDrawn = 0;
    public int trackpointsPause = 0;
    public int segments = 0;

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public int getTrackpointsPause() {
        return trackpointsPause;
    }

    public void setTrackpointsPause(int trackpointsPause) {
        this.trackpointsPause = trackpointsPause;
    }

    public int getTrackpointsReceived() {
        return trackpointsReceived;
    }

    public void setTrackpointsReceived(int trackpointsReceived) {
        this.trackpointsReceived = trackpointsReceived;
    }

    public int getTrackpointsInvalid() {
        return trackpointsInvalid;
    }

    public void setTrackpointsInvalid(int trackpointsInvalid) {
        this.trackpointsInvalid = trackpointsInvalid;
    }

    public int getTrackpointsDrawn() {
        return trackpointsDrawn;
    }

    public void setTrackpointsDrawn(int trackpointsDrawn) {
        this.trackpointsDrawn = trackpointsDrawn;
    }

    public void add(final TrackPointsDebug other) {
        this.setTrackpointsReceived(this.getTrackpointsReceived() + other.getTrackpointsReceived());
        this.setTrackpointsInvalid(this.getTrackpointsInvalid() + other.getTrackpointsInvalid());
        this.setTrackpointsDrawn(this.getTrackpointsDrawn() + other.getTrackpointsDrawn());
        this.setTrackpointsPause(this.getTrackpointsPause() + other.getTrackpointsPause());
        this.setSegments(this.getSegments() + other.getSegments());
    }
}
