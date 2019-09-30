ALTER TABLE event ALTER COLUMN description TYPE varchar(500);


UPDATE event SET description= 'The next outstanding show is Monday March 23rd at 8pm. The Comedy Cabaret offers a Laffy Raffy Raffle. To enter all you have to do is RSVP/Join this event, and then on the night of show, be present in the flesh and your name will be entered into a draw for free beer! (DISCLAIMER: If you’re not there when we draw your name you forfeit your mouth-watering super-thirst quenching beer. Also must 19 years of age or older to enter).', name= 'Incentives', place= 'Bialystok', topic= 'Comedy' WHERE id = 1;
UPDATE event SET description= 'Fundraising proceeds will directly support Reel Asian’s year-round and festival youth programs that help culturally diverse and newcomer youth from the GTA examine issues of identity and belonging through media arts, while providing leadership opportunities, education and job-skills training in film and creative production.', name= 'Charity', place= 'Berlin', topic= 'Reel Asian Film Festival', started= true WHERE id = 2;
UPDATE event SET description= 'Big Sean will at the hmv Underground at 333 Yonge Street on March 24, 2015 at 5:00 PM for an exclusive FAN MEET & GREET. Space is limited to the first 300 fans on a first come first served basis (as per the event protocol)', name= 'Urgency & FOMO', place= 'Warsaw', topic= 'Millenniums' WHERE id = 3;
UPDATE event SET description= 'Techweek curates exciting programming that allows a global spotlight to shine on each ecosystem and its leaders. Past speakers include Rahm Emanuel, Travis Kalanick (CEO, Uber), Craig Newmark (Founder, Craigslist), Barney Harford (CEO, Orbitz), and Chuck Templeton (Founder, OpenTable). The Techweek expo has gathered more than 200 sponsors, including companies such as Google, Groupon, Microsoft, Motorola, Redbox, Uber, and Wordpress.', name= 'Social Proof', place= 'Cracow', topic= 'Techweek' WHERE id = 4;


UPDATE task_status SET name= 'Buy onions and water', task_status_type = 'adhoc' WHERE id = 1;
UPDATE task_status SET name= 'Buy soda' WHERE id=2;
UPDATE task_status SET name= 'Buy microphones' WHERE id=3;
UPDATE task_status SET name= 'Invite guests', task_status_type = 'Typical event' WHERE id = 4;
UPDATE task_status SET name= 'Invite speaker', task_status_type = 'Typical event' WHERE id = 5;
UPDATE task_status SET name= 'Gather the team', task_status_type = 'Scrum methodology' WHERE id = 6;
UPDATE task_status SET name= 'Make code review to pull requests', task_status_type = 'Scrum methodology' WHERE id = 7;


UPDATE to_do_predefined SET name =  'Typical event' WHERE id = 1;
UPDATE to_do_predefined SET name =  'Scrum methodology' WHERE id = 2;
UPDATE to_do_predefined SET name =  'Shopping' WHERE id = 3;
UPDATE to_do_predefined SET name =  'Managing speaker' WHERE id = 4;
UPDATE to_do_predefined SET name =  'Decorating the room' WHERE id = 5;
UPDATE to_do_predefined SET name =  'Prepare food with minimum amount of time' WHERE id = 6;
UPDATE to_do_predefined SET name =  'Event at university of technology' WHERE id = 7;


UPDATE task SET description = 'Invite guests' WHERE description = 'TRESC TASKA 1';
UPDATE task SET description = 'Invite speaker' WHERE description = 'TRESC TASKA 2';
UPDATE task SET description = 'Gather the team' WHERE description = 'TRESC TASKA 3';
UPDATE task SET description = 'Make code review to pull requests', id = 2 WHERE description = 'TRESC TASKA 4';
UPDATE task SET description = 'Buy bananas' WHERE description = 'TRESC TASKA 5';
UPDATE task SET description = 'Buy energy drinks' WHERE description = 'TRESC TASKA 6';
UPDATE task SET description = 'Buy pea soup' WHERE description = 'TRESC TASKA 7';
UPDATE task SET description = 'Organize transport for our lovely speaker' WHERE description = 'TRESC TASKA 8';
UPDATE task SET description = 'Accommodate a speaker' WHERE description = 'TRESC TASKA 9';