 (define (problem shakey_problem)
 (:domain shakey_domain)
 (:objects
   r1, r2, r3 - room
   gl, gr - grip
   o1, o2, o3 - object
   l1, l2, l3 - light-switch
   s - robot
   d1, d2 - wide_door
   d3 - narrow_door
   b - box
   )
(:init
   ;; Type declarations:
   ;;(object-at	o1)
   ;;declare all stuff in the world
   ;;everything initialized is true
    (object-in	o1 r1) ;;object 1-7 in different rooms
    (object-in	o2 r3)
    (object-in	o3 r2)

    (robot-at s r2) ;;shakey starts in room2

    (wide-in d1 r1) ;;places the wide doors
    (wide-in d2 r2)
    (wide-in d1 r2)
    (wide-in d2 r3)
    (empty gl) ;;both grippers are free
    (empty gr)
    (box-in b r2)
    (adjacent r1 r2)
    (adjacent r2 r3)
    (adjacent r2 r1)
    (adjacent r3 r2)

 )
 ;; our goal is to light up all rooms,
 ;; and leave object1 in room2
 (:goal   (and (on l1) (on l2 ) (on l3) (object-in o1 r2) (box-in b r3)))
)
