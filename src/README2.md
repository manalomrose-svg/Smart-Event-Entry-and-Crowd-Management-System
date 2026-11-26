# Task 2.01 - Core Classes

## Completed task Assign (for me)
- Created 12 Java classes based on the UML diagram.
- Added private fields, constructors, and getters.
- Added basic methods and some input validation.
- Added Status enum for guest status.
- Successfully compiled and passed 28 basic checks.

## Pending Methods (To Be do)(Some are Placeholder)

| Class | Pending functionality |
|---|---|
| AvailabilityChecker | Check and block schedules |
| CrowdControl | Update crowd count and alerts |
| EntryController | Allow and deny guest entry |
| EventReport | Generate and search reports 
| Staff | Mark guest exit and view crowd count |
| Reservation | Confirm reservation |
| DeniedEntry | Manage denied entry records |

## Additional Notes
- Methods with TODO comments are not yet implemented.

- CrowdControl is used instead of CrowdCounter based on the UML class name.  (Paki check po UML may magkaiba po name na same func if im correct)

- LocalDate and LocalTime are used for date and time.

- Reservation numbers are generated using UUID.

- GuestList currently rejects duplicate names.

- EntryRecord returns -1 for stay time if the guest has not exited yet.

- Further validation and integration are needed for the succeeding tasks the other task.

- AI assistance was used in developing the initial code.( so plss ask me if my problems).

>Pwede po ito i promt pagdi na understand ung code i delete din po after.