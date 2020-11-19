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
       (adjacent ?r1 ?r2 - room)
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
  :effect (and (not (holding ?g ?o) ) (empty ?g) )

  )

  (:action move ;; move without olbject
    :parameters (?from ?to - room ?s - robot ?d - wide_door ?g - grip)
    :precondition (and (robot-at ?s ?from)
                  (wide-in ?d ?from)
                  (empty ?g)
                  (adjacent ?from ?to)
                  )
    :effect (robot-at ?s ?to)
    )
    (:action move_object_to_room ;; move with object
      :parameters (?from ?to - room ?s - robot ?d - wide_door ?g - grip ?o - object)
      :precondition (and (robot-at ?s ?from)
                    (wide-in ?d ?from)
                    (not (empty ?g))
                    (holding ?g ?o)
                    (adjacent ?from ?to))

      :effect (and (robot-at ?s ?to) (object-in ?o ?to) )
      )

(:action push_to_lightswitch;;inroom
  :parameters(?r - room ?s - robot ?b - box ?l - light-switch)
  :precondition(and (robot-at ?s ?r ) (box-in ?b ?r) (attached ?l ?r) (not (box-in-pos ?b ?l)) )
  :effect(box-in-pos ?b ?l)

  )
(:action turn_on_light
  :parameters (?r - room ?s - robot ?b - box ?l - light-switch)
  :precondition (and (robot-at ?s ?r ) (box-in ?b ?r) (box-in-pos ?b ?l) (not (on ?l)) (attached ?l ?r))
  :effect(on ?l)
  )

(:action push_box_to_room
  :parameters (?from ?to - room ?s - robot ?b - box ?d - wide_door)
  :precondition (and (box-in ?b ?from) (robot-at ?s ?from) (wide-in ?d ?from)(adjacent ?from ?to) )
  :effect (and (box-in ?b ?to) (robot-at ?s ?to))
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
