(define (domain shakey_domain)
  (:requirements :strips :typing :equality )

   (:types
		   room
       wide_door

       box
       light-switch
       robot
       grip
       object
       )
       (:predicates
       (object-in	?o - object ?r - room) ; is object ?o in room ?r
       (holding ?g - grip ?o - object); is grip ?g holdig object ?o
		   (empty ?g - grip)				;is grip ?g empty
       (on ?l  - light-switch ) ;is light ?l on
       (attached ?l  - light-switch ?r - room) ;is light ?l attached in room ?r
       (robot-at ?s - robot ?r - room) ; is robot ?s in room ?r
       (wide-between ?d - wide_door ?r1 ?r2 - room) ;is there a wide door ?d between room ?r1 ?r2
       (box-in ?b - box ?r - room) ; is box ?b in room ?r
       (box-in-pos ?b - box ?l - light-switch) ; is box ?b positioned under light-switch ?l
       (adjacent ?r1 ?r2 - room) ;is room r1 adjacent to room r2
		)

;;carry object in any grip g
   (:action carry
     :parameters (?o - object  ?g - grip ?s - robot ?r - room ?l - light-switch)
     :precondition (and (object-in ?o ?r)
							(empty 	?g)
              (robot-at ?s ?r)
              (attached ?l ?r)
              (on ?l)
							)
     :effect (and (holding	?g 	?o)
							 (not (empty ?g))
     )
)
;;drop object from grip g in room r
(:action drop_object
  :parameters(?g - grip ?r - room ?s - robot ?o - object)
  :precondition (and (not (empty ?g))
                (holding ?g ?o)
                (robot-at ?s ?r)
                )
  :effect (and (not (holding ?g ?o))  (empty ?g) (object-in ?o ?r)  )

  )
;;move to room with our without object
  (:action move
    :parameters (?from ?to - room ?s - robot ?d - wide_door)
    :precondition (and (robot-at ?s ?from)
                  (or (wide-between ?d ?from ?to) (wide-between ?d ?to ?from))
                  (or (adjacent ?from ?to)(adjacent ?to ?from)
                  ) )
    :effect (and (robot-at ?s ?to) (not (robot-at ?s ?from)) )
    )



;;push box to position in the same room under lightswitch
(:action push_to_lightswitch
  :parameters(?r - room ?s - robot ?b - box ?l - light-switch)
  :precondition(and (robot-at ?s ?r ) (box-in ?b ?r) (attached ?l ?r) (not (box-in-pos ?b ?l)) )
  :effect(box-in-pos ?b ?l)

  )
;;turn on lights in the room if the box is in position
(:action turn_on_light
  :parameters (?r - room ?s - robot ?b - box ?l - light-switch)
  :precondition (and (robot-at ?s ?r ) (box-in ?b ?r) (box-in-pos ?b ?l) (not (on ?l)) (attached ?l ?r))
  :effect(on ?l)
  )
;;push box from one room to another
(:action push_box_to_room
  :parameters (?from ?to - room ?s - robot ?b - box ?d - wide_door)
  :precondition (and (box-in ?b ?from) (robot-at ?s ?from) (or (wide-between ?d ?from ?to) (wide-between ?d ?to ?from))
                (or (adjacent ?from ?to)(adjacent ?to ?from) ) )
  :effect (and (box-in ?b ?to) (robot-at ?s ?to) (not (box-in ?b ?from)) (not (robot-at ?s ?from)))
  )


)
