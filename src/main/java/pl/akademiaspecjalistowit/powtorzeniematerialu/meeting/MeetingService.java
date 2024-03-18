package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.List;
import java.util.Set;

public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService() {
        meetingRepository = new MeetingRepository();
    }

    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString, Set<String> participantEmail,
                                    String meetingDuration) {

        Meeting meeting = new Meeting(meetingName, meetingDateTimeString, participantEmail, meetingDuration);

        List<Meeting> meetingsAtTheseSameDay = getMeetingsAtTheseSameDay(meeting);
        meeting.checkForParticipantsAlreadyScheduledMeetingsCollisions(meetingsAtTheseSameDay);

        meetingRepository.save(meeting);
        return meeting;
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    private List<Meeting> getMeetingsAtTheseSameDay(Meeting meeting) {
        return meetingRepository.findAllByDate(meeting.getDateAndTime().toLocalDate());
    }


}
