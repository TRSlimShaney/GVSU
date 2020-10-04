import random

import pygame

from Sprites.Projectile import Projectile


class Enemy(pygame.sprite.Sprite):
    def __init__(self, image, left, top):
        pygame.sprite.Sprite.__init__(self)
        self.speed = [-3, 0]
        self.image = image
        self.rect = self.image.get_rect()
        self.rect = self.rect.move(left, top)
        self.leftStop = left - 200
        self.rightStop = left + 200

    # move the enemy
    def move(self):
        self.rect = self.rect.move(self.speed)
        if self.rect.right > self.rightStop:
            self.rect.right = self.rightStop
            self.speed[0] = -self.speed[0]
        if self.rect.left < self.leftStop:
            self.rect.left = self.leftStop
            self.speed[0] = -self.speed[0]

    # handle if the enemy shoots
    def shoot(self, game):
        number = random.randint(1, 500)
        if (number > 499):
            shot = Projectile(self.rect.left, self.rect.top + 100, 2)
            game.shots.add(shot)