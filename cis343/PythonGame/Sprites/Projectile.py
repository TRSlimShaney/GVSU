import pygame

class Projectile(pygame.sprite.Sprite):
    def __init__(self, left, top, who):
        pygame.sprite.Sprite.__init__(self)
        self.bang = pygame.mixer.Sound('wav/bang44.wav')
        self.die = pygame.mixer.Sound('wav/enemyDead.wav')
        self.shot = pygame.mixer.Sound('wav/playerShot.wav')
        if who == 1:
            self.image = pygame.image.load('Sprites/laserGreen08.png').convert()
        elif who == 2:
            self.image = pygame.image.load('Sprites/laserRed04.png').convert()
        self.rect = self.image.get_rect()
        self.who = who
        if who == 1:
            self.speed = [0, -15]
            self.shot.play()
        elif who == 2:
            self.speed = [0, 15]
        self.rect = self.rect.move(left, top)

    # move the projectile
    def move(self):
        self.rect = self.rect.move(self.speed)

    # handle if the projectile hits something or goes off screen.
    def update(self, game, enemies, ship):
        if self.rect.top < 0:
            game.shots.remove(self)
            return
        if self.who == 1:
            for e in enemies:
                hitObject = pygame.sprite.collide_rect(self, e)
                if (hitObject):
                    self.die.play()
                    e.kill()
                    game.shots.remove(self)
                    game.score += 1
        elif self.who == 2:
            hitObject = pygame.sprite.collide_rect(self, game.ship)
            if (hitObject):
                self.bang.play()
                game.ship.kill()
                pygame.event.post(game.new_life_event)
                game.shots.remove(self)