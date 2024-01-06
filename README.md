# Pacman

## Description du Projet
Le projet consiste en la réalisation d’un jeu inspiré du célèbre jeu Pacman. La partie se déroule sur une grille 2D représentant un labyrinthe vu de dessus. L'objectif du jeu est de déplacer Pacman à travers le labyrinthe pour manger toutes les pacgommes présentes. Cependant, quatre fantômes se déplacent aléatoirement dans le labyrinthe, et si l'un d'eux touche Pacman, celui-ci perd une vie. Le jeu comprend également des pacgommes bonus aux effets variés.

## récapitulatif des effets des pacgommes selon leur couleur

| Couleur | Points | Effet                                      |
| ------- | ------ | ------------------------------------------ |
| Bleu    | 100    | Aucun effet                                |
| Violet  | 300    | Pacman devient invisible pour les fantômes. Sa couleur devient jaune pâle. |
| Orange  | 500    | Pacman devient un superpacman. Sa couleur devient orange et les fantômes deviennent bleus. |
| Vert    | 1000   | Modifie la structure du labyrinthe.         |

N'hésitez pas à me faire savoir si vous avez besoin de plus d'informations ou de modifications.

## Règles du Jeu
- Initialement, le joueur dispose de trois vies.
- Le joueur obtient une vie supplémentaire en dépassant les 5000 points.
- Chaque fantôme se déplace dans une direction jusqu'à ce qu'il atteigne un mur, puis choisit une nouvelle direction aléatoirement.
- Quand Pacman est invisible, il peut traverser les fantômes sans perdre de vie.
- Quand Pacman est un superpacman, les fantômes deviennent vulnérables, se déplaçant plus lentement et retournant au centre du labyrinthe s'ils sont touchés.
- Le jeu se termine soit lorsque toutes les pacgommes sont mangées (victoire), soit lorsque Pacman perd toutes ses vies (défaite).

### Point Clé
- Utilisation du patron de conception State pour la gestion des états des personnages.
- Hiérarchisation et factorisation des éléments de la grille de jeu.
- Indépendance des aspects logiques du jeu de leur affichage, utilisant éventuellement une architecture Observateur/Observé.
- Conception permettant un ajout facile de nouvelles règles et de nouveaux éléments de terrain.
- Code propre, compilable sans erreurs, méthodes courtes, lisibles, et sans duplication de code.
- Strict respect de l'accès aux informations nécessaires pour chaque objet, sans utilisation d'images ou de sprites.

L'accent est mis sur la propreté et la structuration du code plutôt que sur les graphismes ou la diversité des niveaux. Un niveau de test couvrant toutes les fonctionnalités est largement suffisant.
