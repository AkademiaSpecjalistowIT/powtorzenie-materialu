package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MeetingTest {

    @Test
    void should_throw_exception_for_overlapping_meetings() {
        // Given
        Meeting existingMeeting = new Meeting(
            "Existing Meeting",
            "15:03:2023 10:00",
            Set.of("participant@example.com"),
            "01:00"
        );

        Meeting newMeeting = new Meeting(
            "New Meeting Overlapping",
            "15:03:2023 10:30",
            Set.of("participant@example.com"),
            "01:00"
        );

        // When
        Executable action = () ->
            newMeeting.checkForParticipantsAlreadyScheduledMeetingsCollisions(List.of(existingMeeting));

        // Then
        assertThrows(MeetingException.class, action);
    }

    @Test
    void should_not_throw_exception_for_meetings_with_no_collisions() {
        // Given
        Meeting existingMeeting = new Meeting(
            "Existing Meeting",
            "15:03:2023 09:00", // dd:MM:yyyy HH:mm
            Set.of("participant@example.com"),
            "01:00"
        );

        Meeting newMeeting = new Meeting(
            "New Meeting Not Overlapping",
            "15:03:2023 10:00", // Does not overlap with existing meeting
            Set.of("participant@example.com"),
            "01:00"
        );

        // When
        Executable action = () ->
            newMeeting.checkForParticipantsAlreadyScheduledMeetingsCollisions(List.of(existingMeeting));

        // Then
        assertDoesNotThrow(action);
    }

    @Test
    void should_not_throw_exception_for_meetings_with_collisions_but_different_participants() {
        // Given
        Meeting existingMeeting = new Meeting(
            "Existing Meeting",
            "15:03:2023 09:00", // dd:MM:yyyy HH:mm
            Set.of("participant1@example.com"),
            "02:00"
        );

        Meeting newMeeting = new Meeting(
            "New Meeting Not Overlapping",
            "15:03:2023 10:00", // Does not overlap with existing meeting
            Set.of("participant2@example.com"),
            "01:00"
        );

        // When
        Executable action = () ->
            newMeeting.checkForParticipantsAlreadyScheduledMeetingsCollisions(List.of(existingMeeting));

        // Then
        assertDoesNotThrow(action);
    }
}

