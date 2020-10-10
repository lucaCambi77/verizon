/** */
package it.cambi.verizon.controller;

import com.google.common.base.Strings;
import it.cambi.verizon.api.AppointmentApi;
import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;
import it.cambi.verizon.service.AppointmentProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/** @author luca */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/appointment")
public class AppointmentController implements AppointmentApi {

  private @Autowired AppointmentProxyService appointmentProxyService;

  @GetMapping("/test")
  public String home() {
    return "Hello Appointment!";
  }

  @Override
  @GetMapping(value = "/{id}")
  public Appointment findAppointmentById(
      @PathVariable("id") String id, @RequestParam("type") AppointmentType type) {
    return appointmentProxyService.findAppointmentById(id, type);
  }

  @Override
  @GetMapping
  public List<Appointment> findAppointments() {
    return appointmentProxyService.findAppointments();
  }

  @Override
  @GetMapping(value = "/day/{day}")
  public List<Appointment> findAppointmentsByDay(@PathVariable("day") String day) {
    return appointmentProxyService.findAppointmentsByDay(day);
  }

  @Override
  @GetMapping("attendeeByDay")
  public List<Appointment> findAppointmentsOfAttendeeByDay(
      @RequestParam("day") String day, @RequestParam("attendee") String attendee) {
    return appointmentProxyService.findAppointmentsofAttendeeByDay(day, attendee);
  }

  @Override
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Appointment saveAppointment(@RequestBody Appointment appointment) {
    performArgumentChecks(appointment);
    return appointmentProxyService.saveAppointment(appointment);
  }

  @Override
  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Appointment updateAppointment(@RequestBody Appointment appointment) {
    performArgumentChecks(appointment);
    return appointmentProxyService.saveAppointment(appointment);
  }

  @Override
  @DeleteMapping
  public boolean deleteAppointment(@RequestBody Appointment appointment) {
    return appointmentProxyService.deleteAppointment(appointment);
  }

  private void performArgumentChecks(Appointment appointment) {
    checkArgument(appointment != null);
    checkArgument(appointment.getAddress() != null);
    checkArgument(appointment.getType() != null);
    checkArgument(!Strings.isNullOrEmpty(appointment.getName()));
    checkArgument(!Strings.isNullOrEmpty(appointment.getDay()));
    checkArgument(!Strings.isNullOrEmpty(appointment.getTime()));
  }
}
