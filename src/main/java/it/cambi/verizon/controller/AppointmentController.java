/** */
package it.cambi.verizon.controller;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import it.cambi.verizon.api.AppointmentApi;
import it.cambi.verizon.domain.Appointment;
import it.cambi.verizon.domain.AppointmentType;
import it.cambi.verizon.service.AppointmentProxyService;

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

  @GetMapping(value = "/{id}")
  public Appointment findAppointmentById(
      @PathVariable("id") String id, @RequestParam("type") AppointmentType type) {
    return appointmentProxyService.findAppointmentById(id, type);
  }

  @GetMapping
  public List<Appointment> findAppointments() {
    return appointmentProxyService.findAppointments();
  }

  @GetMapping(value = "/day/{day}")
  public List<Appointment> findAppointmentsByDay(@PathVariable("day") String day) {
    return appointmentProxyService.findAppointmentsByDay(day);
  }

  @GetMapping("attendeeByDay")
  public List<Appointment> findAppointmentsOfAttendeeByDay(
      @RequestParam("day") String day, @RequestParam("attendee") String attendee) {
    return appointmentProxyService.findAppointmentsOfAttendeeByDay(day, attendee);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Appointment saveAppointment(@RequestBody Appointment appointment) {
    performArgumentChecks(appointment);
    return appointmentProxyService.saveAppointment(appointment);
  }

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Appointment updateAppointment(@RequestBody Appointment appointment) {
    performArgumentChecks(appointment);
    return appointmentProxyService.saveAppointment(appointment);
  }

  @DeleteMapping
  public void deleteAppointment(@RequestBody Appointment appointment) {
    appointmentProxyService.deleteAppointment(appointment);
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
