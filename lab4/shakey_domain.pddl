(define (domain shakey_domain)
  (:requirements :strips :typing :equality )

   (:types
		   room
       wide_door
      ;narrow_door
       box
       light-switch
       robot
       grip
       object
       )
       (:predicates
       (object-in	?o - object ?r - room) ; är det ett object i room
       (holding ?g - grip ?o - object); is grip ?g holdig object ?o
		   (empty ?g - grip)				; is grip ?g empty and is ?g a grip
       (on ?l  - light-switch ) ;är lampan tänd?
       (attached ?l  - light-switch ?r - room) ;lamp in specific room
       (robot-at ?s - robot ?r - room) ; är roboten i rummet?
       (wide-in ?d - wide_door ?r - room) ;bred dörr?
       (box-in ?b - box ?r - room)
       (box-in-pos ?b - box ?l - light-switch)
		)


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
(:action drop_object
  :parameters(?g - grip ?r - room ?s - robot ?o - object)
  :precondition (and (not (empty ?g))
                (holding ?g ?o)
                (robot-at ?s ?r)
                )
  :effect (and (not (holding ?g ?o) ) (empty ?g) (object-in ?o ?r))

  )

  (:action move ;; move with or without object/objects
    :parameters (?from ?to - room ?s - robot ?d - wide_door)
    :precondition (and (robot-at ?s ?from)
                  (wide-in ?d ?from)
                  )
    :effect (robot-at ?s ?to)
    )

(:action push;;inroom
  :parameters(?r - room ?s - shakey ?b - box ?l - light-switch)
  :precondition(and (robot-at ?s ?r ) (box-in ?b ?r) (attached ?l ?r) not(box-in-pos ?b ?l) )
  :effect(box-in-pos ?b ?l)

  )

  (action )

    ;; (:action move_object
      ;;:parameters (?from ?to - room ?s - robot ?d - wide_door ?o - object ?g - grip)
      ;;:precondition (and (robot-at ?s ?from)
        ;;            (wide-in ?d ?from)
          ;;          (not (empty ?g))
            ;;        )
      ;;:effect (and (robot-at ?s ?to)
        ;;      (object-in ?o ?to)
          ;;    )

      ;)

)
