package hristov.mihail.carracing;

import hristov.mihail.carracing.models.Track;
import hristov.mihail.carracing.services.TrackService;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        Track track = new Track("Baku", 200, "Baku");

        ArrayList<Track> tracks = TrackService.getAllTrack();
        for (Track track2 : tracks) {
            System.out.println(track2.getIdTrack());
        }

    }
}
