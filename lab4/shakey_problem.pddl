 (define (problem shakey_problem)
 (:domain shakey_domain)
 (:objects
   r1, r2, r3 - room
   gl, gr - grip
   o1, o2, o3 - object
   l1, l2, l3 - light-switch
   s - robot
   d1, d2 - wide_door
   b - box
   )
(:init
   ;; Type declarations:
   ;;(object-at	o1)
   ;;declare all stuff in the world
   ;;everything initialized is true
    (object-in	o1 r1) ;;object 1-3 in different rooms
    (object-in	o2 r1)
    (object-in	o3 r2)

    (robot-at s r2) ;;shakey starts in room2

    (wide-between d1 r1 r2) ;;wide door d1 is between room r1 r2
    (wide-between d2 r2 r3) ;;wide door d2 is between room r2 r3


    (empty gl) ;;both grippers are free
    (empty gr) ;;both grippers are free
    (box-in b r2) ;box  in room r2
    (adjacent r1 r2) ;room adjacent to eachother
    (adjacent r3 r2);room adjacent to eachother
    (attached l1 r1); lightswitch1 in room1
    (attached l2 r2) ;lightswitch2 in room2
    (attached l3 r3) ;lightswitch3 in room3

 )
 ;; our goal is to light up all rooms,
 ;; and leave object1 and object2 in room3
 ;; and leave box  in room3
 (:goal   (and (on l1) (on l2 ) (on l3) (object-in o1 r3) (object-in o2 r3) (box-in b r3)))

)
