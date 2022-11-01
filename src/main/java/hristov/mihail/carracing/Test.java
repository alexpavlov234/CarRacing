package hristov.mihail.carracing;

import hristov.mihail.carracing.models.Person;
import hristov.mihail.carracing.models.Track;
import hristov.mihail.carracing.models.User;
import hristov.mihail.carracing.services.PersonService;
import hristov.mihail.carracing.services.TrackService;
import hristov.mihail.carracing.services.UserService;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){

        Track track = new Track("Baku", 200, "Baku");
Person person = PersonService.getLastPerson();
        User user = new User("alexpave23@gmail.com", "Mihail123@&", "User", PersonService.getLastPerson().getIdPerson());
        System.out.println(UserService.getUser("mihailhristov234@gmail.com".trim()).getIdUser());
        ArrayList<Track> tracks = TrackService.getAllTrack();
        for (Track track2 : tracks) {
            System.out.println(track2.getIdTrack());
        }

    }
}
