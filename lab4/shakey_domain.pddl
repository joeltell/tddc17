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
       (on ?l - light-switch) ;är lampan tänd?
       (robot-at ?s - robot ?r - room) ; är roboten i rummet?
       (wide-in ?d - wide_door ?r - room) ; bred dörr?
       ;(narrow ?d - narrow_door) ; smal dörr?
		)


   (:action carry
     :parameters (?o - object  ?g - grip ?s - robot ?r - room ?l - light-switch)
     :precondition (and (object-in ?o ?r)
							(empty 	?g)
              (robot-at ?s ?r)
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

  (:action move
    :parameters (?from ?to - room ?s - robot ?d - wide_door)
    :precondition (and (robot-at ?s ?from)
                  (wide-in ?d ?from)
                  )
    :effect (robot-at ?s ?to)
    )

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
