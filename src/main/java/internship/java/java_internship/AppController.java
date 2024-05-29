package internship.java.java_internship;

import internship.java.java_internship.exceptions.FromStringException;
import internship.java.java_internship.exceptions.ResourceNotFoundException;
import internship.java.java_internship.models.CatColor;
import internship.java.java_internship.models.Visit;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AppController {
    private static Integer nextId = 0;
    private static Integer getNextId() {
        return nextId++;
    }
    private static final HashMap<Integer, Visit> visits = new HashMap<>();

    private static Visit findVisitById(int id) throws ResourceNotFoundException {
        return visits.get(id);
    }

    @GetMapping("/visits")
    public List<Visit> getAllVisits() {
        return visits.values().stream().toList();
    }

    @GetMapping("/visit")
    public ResponseEntity<Visit> getVisitById(@RequestParam(value = "id", defaultValue = "") String id) {
        Visit visit;
        try {
            int idNum = Integer.parseInt(id);
            visit = findVisitById(idNum);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } catch (ResourceNotFoundException rnfe) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<>(visit, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/visit")
    public ResponseEntity<String> postVisit(
            @RequestParam(value = "client", defaultValue = "", required = true) String client,
            @RequestParam(value = "catName", defaultValue = "", required = true) String catName,
            @RequestParam(value = "catAge", defaultValue = "", required = true) int catAge,
            @RequestParam(value = "catColor", defaultValue = "", required = true) String catColor,
            @RequestParam(value = "date", defaultValue = "", required = true) String date,
            @RequestParam(value = "hour", defaultValue = "", required = true) int hour
    ) {
        LocalDate dateOfVisit = LocalDate.parse(date);
        LocalTime timeOfVisit = LocalTime.of(hour, 0);
        LocalDateTime visitDate = LocalDateTime.of(dateOfVisit, timeOfVisit);

        CatColor color;
        try {
            color = CatColor.fromString(catColor);
        } catch (FromStringException fse) {
            return new ResponseEntity<>(
                    "Unknown color. Known color values: " +
                            CatColor.allColors(),
                    HttpStatusCode.valueOf(400));
        }

        int id = getNextId();
        visits.put(id, new Visit(id, visitDate, client, catName, catAge, color));

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PutMapping("/visit/{id}")
    public ResponseEntity<String> editVisit(
            @PathVariable(value = "id", required = true) int id,
            @RequestParam(value = "client", required = false) String client,
            @RequestParam(value = "catName", required = false) String catName,
            @RequestParam(value = "catAge", required = false) Integer catAge,
            @RequestParam(value = "catColor", required = false) String catColor,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "hour", required = false) Integer hour
    ) {
        try {
            Visit visit = findVisitById(id);
            if (client != null) visit.setClient(client);
            if (catName != null) visit.setCatName(catName);
            if (catAge != null) visit.setCatAge(catAge);
            if (catColor != null) visit.setCatColor(CatColor.fromString(catColor));
            if (date != null) {
                LocalDate dateOfVisit = LocalDate.parse(date);
                LocalTime timeOfVisit = visit.getDate().toLocalTime();
                LocalDateTime visitDate = LocalDateTime.of(dateOfVisit, timeOfVisit);
                visit.setDate(visitDate);
            }
            if (hour != null) {
                LocalDate dateOfVisit = visit.getDate().toLocalDate();
                LocalTime timeOfVisit = visit.getDate().toLocalTime();
                LocalDateTime visitDate = LocalDateTime.of(dateOfVisit, timeOfVisit);
                visit.setDate(visitDate);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Visit of id " + id + " not found", HttpStatusCode.valueOf(200));
        } catch (FromStringException fse) {
            return new ResponseEntity<>(
                    "Unknown color `" + catColor +"`. Known color values: " +
                            CatColor.allColors(),
                    HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/visit/{id}")
    public ResponseEntity<String> deletevisit(@PathVariable(value = "id", required = true) int id) {
        try {
            Visit visit = findVisitById(id);
            visits.remove(visit.getId());
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Visit ID not provided", HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>("Visit successfully deleted", HttpStatusCode.valueOf(200));
    }

}
