# hotel_management_system
# can be used by the CEO or staffs of hotel.

Interface:

| interface          | frontend->backend                   | Return value                                   | Meaning of return value                              | Notes                                                        |
| ------------------ | ----------------------------------- | ---------------------------------------------- | ---------------------------------------------------- | ------------------------------------------------------------ |
| /login             | mID, password                       | boolean                                        | True: success loginFalse: fail                       | Depends on who is login our app. If the manager logs in this app, he/she can only manipulate data stored in the hotel where he/she is currently working in. But the CEO can access all info. |
| /checkRoom         | hID                                 | Room Entity                                    | All of rooms of that hotel                           |                                                              |
| /updateRoom        | hID, roomNumber, price, state, kind | boolean                                        | True: successFalse: fail                             |                                                              |
| /addWorker         | worker Entity                       | boolean                                        | True: successFalse: fail                             |                                                              |
| /deleteWorker      | wID                                 | boolean                                        | True: successFalse: fail                             |                                                              |
| /listWorkers       | dID, hID                            | Worker Entity                                  | All the workers in a department of a specific hotel  | Users provide the dID and hID, then return a list of workers working in the specific department and hotel. |
| /checkWorker’sType | wID                                 | Worker ISA hierarchy                           | Returns whether the worker is part time or full time | Return workers(part time/full time)                          |
| /checkMembership   | cID                                 | boolean                                        | True: is a memberFalse: is not a member              | User can check a customer has a membership or not by passing the customer’s ID. |
| /assign Membership | cID, and customer’s information     | boolean                                        | Whether assign the membership successfully.          | User can assign the membership to a customer. ( add the customer into our membership list) |
| /checkBill         | cID                                 | Bill entity                                    |                                                      | Users can get a list of bills associated with the cID.       |
| /addCustomer       | cID and customer’s info             | boolean                                        |                                                      | Users can store a customer entity into the database. And update the room’s status. |
| /checkAllCustomer  | hID                                 | Customer's entity                              |                                                      | Return all customers’ entity of a specific hotel.            |
| /addHotel          | hID, hotel info                     | boolean                                        |                                                      | CEO can propose to build a new hotel                         |
| /checkHotel        | hID                                 | Hotel's entity                                 |                                                      |                                                              |
| /checkCompany      | cName                               | Return a list of hotels belong to that company |                                                      |                                                              |

Current progress:

- [x] /login
- [x] /checkRoom
- [ ] /addRoom
- [ ] /updateRoom
- [ ] /addWorker
- [ ] /deleteWorker
- [x] /listWorkers
- [x] /checkWorkerType
- [x] /checkMembership
- [ ] /assignMembership
- [x] /checkBill
- [x] /checkAllCustomer
- [ ] /addCustomer
- [ ] /addHotel
- [ ] /checkHotel
- [x] /checkCompany