 (define (problem shakey_problem)
 (:domain shakey_domain)
 (:objects
   r1, r2, r3 - room
   gl, gr - grip
   o1 - object
   l1, l2, l3 - light-switch
   s - robot
   d1, d2 - wide_door
   d3 - narrow_door
   )
(:init
   ;; Type declarations:
   ;;(object-at	o1)
    (object-in	o1 r1)
   ;;(empty gl)	(empty gr)
    (robot-at s r2)
 )

 (:goal   (and (on l1) (on l2) (on l3) (object-in o1 r2)))
)
