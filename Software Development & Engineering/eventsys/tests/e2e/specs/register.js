describe('Register', () => {

  before(function () {
    cy.log('Clearing database and adding mock data')
    cy.exec('npm run mock:data')
  })

  it('Successfully loads', () => {
    cy.visit('/register')
  })

  it('Has title', () => {
    cy.visit('/register')
    cy.contains('h1', 'Register')
  })
  
  it('Registering an athlete', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("cy-athlete")
    cy.get('input[id=email]').type("cy@cy.cy")
    cy.get('input[id=first]').type("cy")
    cy.get('input[id=last]').type("cy")
    cy.get('input[id=password]').type("cypress")
    cy.get('input[id=password2]').type("cypress")
    cy.get('button[id=btn-register]').click()

    cy.contains('h1', 'Eventsys - the only system you need for your sports-events')
  })

  it('Registering an event-manager', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("cy-eventmanager")
    cy.get('input[id=email]').type("cy@cy.cy")
    cy.get('input[id=first]').type("cy")
    cy.get('input[id=last]').type("cy")
    cy.get('input[id=password]').type("cypress")
    cy.get('input[id=password2]').type("cypress")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.contains('h1', 'Eventsys - the only system you need for your sports-events')
  })

  
  it('Renders an error when creating user that already exists.', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("cy-eventmanager")
    cy.get('input[id=email]').type("cy@cy.cy")
    cy.get('input[id=first]').type("cy")
    cy.get('input[id=last]').type("cy")
    cy.get('input[id=password]').type("cypress")
    cy.get('input[id=password2]').type("cypress")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })
  
  it('Renders an error when missing username', () => {
    cy.visit('/register')
    
    cy.get('input[id=email]').type("nonexistant@dummy.com")
    cy.get('input[id=first]').type("nonexistant")
    cy.get('input[id=last]').type("nonexistant")
    cy.get('input[id=password]').type("nonexistant")
    cy.get('input[id=password2]').type("nonexistant")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })
  
  it('Renders an error when missing email', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("nonexistant")
    cy.get('input[id=first]').type("nonexistant")
    cy.get('input[id=last]').type("nonexistant")
    cy.get('input[id=password]').type("nonexistant")
    cy.get('input[id=password2]').type("nonexistant")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })

  it('Renders an error when missing first name', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("nonexistant")
    cy.get('input[id=email]').type("nonexistant@dummy.com")
    cy.get('input[id=last]').type("nonexistant")
    cy.get('input[id=password]').type("nonexistant")
    cy.get('input[id=password2]').type("nonexistant")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })

  it('Renders an error when missing last name', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("nonexistant")
    cy.get('input[id=email]').type("nonexistant@dummy.com")
    cy.get('input[id=first]').type("nonexistant")
    cy.get('input[id=password]').type("nonexistant")
    cy.get('input[id=password2]').type("nonexistant")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })

  it('Renders an error when missing password', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("nonexistant")
    cy.get('input[id=email]').type("nonexistant@dummy.com")
    cy.get('input[id=first]').type("nonexistant")
    cy.get('input[id=last]').type("nonexistant")
    cy.get('input[id=password2]').type("nonexistant")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })

  it('Renders an error when missing second password', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("nonexistant")
    cy.get('input[id=email]').type("nonexistant@dummy.com")
    cy.get('input[id=first]').type("nonexistant")
    cy.get('input[id=last]').type("nonexistant")
    cy.get('input[id=password]').type("nonexistant")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })

  it('Renders an error when passwords are not alike', () => {
    cy.visit('/register')
    
    cy.get('input[id=username]').type("nonexistant")
    cy.get('input[id=email]').type("nonexistant@dummy.com")
    cy.get('input[id=first]').type("nonexistant")
    cy.get('input[id=last]').type("nonexistant")
    cy.get('input[id=password]').type("nonexistant")
    cy.get('input[id=password2]').type("non")
    cy.get('select[id=userlevel]').select('Event Manager')
    cy.get('button[id=btn-register]').click()

    cy.get('.alert')
  })
})