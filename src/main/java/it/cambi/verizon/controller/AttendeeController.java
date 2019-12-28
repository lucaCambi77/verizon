/**
 * 
 */
package it.cambi.verizon.controller;

import static com.google.common.base.Preconditions.checkArgument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import it.cambi.verizon.domain.Attendee;
import it.cambi.verizon.service.AttendeeService;

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
