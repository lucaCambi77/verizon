/**
 *
 */
package it.cambi.verizon.controller;

import com.google.common.base.Strings;
import it.cambi.verizon.domain.Attendee;
import it.cambi.verizon.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author luca
 *
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/attendee")
public class AttendeeController
{
    private @Autowired AttendeeService attendeeService;

    @GetMapping("/test")
    public String home()
    {
        return "Hello Attendee!";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Attendee save(@RequestBody Attendee attendee)
    {
        performArgumentChecks(attendee);
        return attendeeService.save(attendee);
    }

    private void performArgumentChecks(Attendee attendee)
    {
        checkArgument(attendee != null);
        checkArgument(!Strings.isNullOrEmpty(attendee.getName()));
        checkArgument(!Strings.isNullOrEmpty(attendee.getEmail()));

    }
}
