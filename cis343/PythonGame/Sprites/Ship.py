import pygame

from Sprites.Projectile import Projectile


class Ship(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.speed = [0, 0]
        self.image = pygame.image.load('Sprites/playerShip3_green.png').convert()
        self.rect = self.image.get_rect()
        self.rect = self.rect.move(480, 1100)

    def setSpeed(self, x, y):
        self.speed = [x, y]

    def move(self):
        self.rect = self.rect.move(self.speed)
        if self.rect.right > 936:
            self.rect.right = 936
        if self.rect.left < 24:
            self.rect.left = 24

    def shoot(self, game):
        shot = Projectile(self.rect.left, self.rect.top - 100, 1)
        game.shots.add(shot)