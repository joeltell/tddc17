 (define (problem shakey_problem)
 (:domain shakey_domain)
 (:objects
   r1,r2,r3 - room
   gl, gr - grip
   o1, o2 - object
   )
(:init
   ;; Type declarations:
   ;;(object-at	o1)   
    (object-at	o2)
   ;;(empty gl)	(empty gr)
 )
 (:goal   (and (lights) (object-at o1 r2)))
)
