(define (domain shakey_domain)
  (:requirements :strips :typing :equality )

   (:types
		room
      ;; door
      ;; box
      ;; switch
       robot
       grip
       object
       )
       (:predicates
       (object-in	?o - object ?r - room) ; är det ett object i room
       (holding		?g - grip 	?o - object); is grip ?g holdig object ?o
		(empty		?g - grip)				; is grip ?g empty and is ?g a grip
		)
   
	
   (:action carry
     :parameters ( ?o - object ?g - grip ?s - robot)
     :precondition  	 	(and (object-at ?o)
							(empty 	?g)
							) 
     :effect (and (not(object-at ?o))
							 (holding	?g 	?o)
							 (not (empty ?g))
     )
)

)
