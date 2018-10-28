                                                     
 GOLVE <br/>
Game Of Life, Virus Edition <br/>
 <br/>
What is GOL-VE? -- <br/>
Game Of Life, Virus Edition - the traditional Game Of Life game (https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) 
with a virus twist: starting a pre-defined generation, a virus spreads! the horror!! and in the new order, these are the rules:
1. Any dead cell with a single live neighbor lives on to the next generation.
2. Any live cell with no horizontal or vertical live neighbors dies.
 <br/>
Why is GOL-VE useful --  <br/>
It's a nice excersize and also a fun game. who needs Fortnite. <br/>
 <br/>
How users can get started with the GOLVE --  <br/>
execute main with the following args - width, height, infect-after, max-generations, seed, interval <br/>
-width and height of the seed universe to start from <br/>
-infect-after - at which generation you wish the horrible virus to spread <br/>
-max-generation - how many generations you want the game to run <br/>
-seed - a flat representation of the seed universe <br/>
-interval - what's the interval [sec] you wish between 2 generations <br/>
-example: 3 3 3 10 "0 0 0 1 0 0 1 0 1" 1 <br/>
 <br/>
Where users can get help with GOVLE --  <br/>
pazbarda@gmail.com. that's me. <br/>
 <br/>
Who maintains and contributes to GOLVE --  <br/>
pazbarda@gmail.com. that's also me. <br/>
 <br/>
 Code and Design --  <br/>
 The application creates a game session for each client, runs the client's seed universe for the client's predefined max generations
 and displays each generation outcome. <br/> when last generation passed the session ends and app will close. <br/>
 application main is written with a single session for a single user, but it can be easily changed to handle several clients with several auto-closing sessions. <br/>
 current client implementation is merely the local console output, but it could easily be implemented to act as delegate for remote client etc. <br/>
 the default rules are the virus rules, but user (client) can specify an external file (only json supported, at the moment) with their own wako rules :) it's really fun you should try it! <br/>
 test coverage - not great, only 2 classes are covered, BUT - the existing tests indirectly test some functionality of other classes, so for the given time for the task it seems decent (to me at least :)) <br/>
 room for improvements - lots. in several places in the code there are 'TODO' comments where obviouse improvements are needed, including missing tests.
