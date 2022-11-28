
package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import io.swagger.annotations.*;


import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/", description = "Endpoint to Track Service")
@Path("/shop")
public class ShopService {

    private GameManager tm;

    public ShopService() throws EmailAlreadyBeingUsedException {
        this.tm = GameManagerImpl.getInstance();
        if (tm.size()==0) {
            this.tm.addUser("Alba","Roma", "23112001","albaroma@gmail.com","123456");
            this.tm.addUser("Maria","Ubiergo", "02112001","meri@gmail.com","123456");
            this.tm.addUser("Guillem","Purti", "02112001","guille@gmail.com","123456");

            this.tm.addGadget("1",3,"Ojo volador","afewifp");
            this.tm.addGadget("2",8,"Espada sin filo","afeoejifp");
            this.tm.addGadget("3",20,"Caminacielos","afeoejep");
            this.tm.addGadget("4",2,"Percha sonica","afeoe");
        }
    }
    @GET
    @ApiOperation(value = "Gives the shop gadgets", notes = "ordered by price")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Gadget.class, responseContainer="List")
    })
    @Path("/gadget/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGadgets() {

        List<Gadget> gadgetList = this.tm.gadgetList();
        GenericEntity<List<Gadget>> entity = new GenericEntity<List<Gadget>>(gadgetList) {};
        return Response.status(201).entity(entity).build();

    }
    @GET
    @ApiOperation(value = "Gives a Gadget by id", notes = "With an id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Gadget.class),
            @ApiResponse(code = 404, message = "Gadget does not exist")
    })
    @Path("/gadget/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGadget(@PathParam("id") String id) {
        try {
            Gadget gadget = this.tm.getGadget(id);
            return Response.status(201).entity(gadget).build();
        }
        catch (GadgetDoesNotExistException E){
            return Response.status(404).build();
        }

    }
    @POST
    @ApiOperation(value = "create a new User", notes = "Do you want to register to our shop?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= UserInformation.class),
            @ApiResponse(code = 409, message = "This user already exists."),
            @ApiResponse(code = 500, message = "Empty credentials")
    })
    @Path("/user/register")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response newUser(UserInformation newUser){
        try{
            this.tm.addUser(newUser.getName(), newUser.getSurname(), newUser.getBirthDate(), newUser.getMail(), newUser.getPassword());
            return Response.status(201).entity(newUser).build();
        }
        catch (EmailAlreadyBeingUsedException E){
            return Response.status(409).entity(newUser).build();
        }
    }
    @POST
    @ApiOperation(value = "Log In to the shop", notes = "Do you want to log in to our shop?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= UserInformation.class),
            @ApiResponse(code = 409, message = "Wrong credentials.")


    })
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response logIn(Credentials credentials){

        try{
            this.tm.LogIn(credentials);
            return Response.status(201).entity(credentials).build();
        }
        catch (IncorrectCredentialsException E){
            return Response.status(409).entity(credentials).build();
        }
    }
    @POST
    @ApiOperation(value = "create a new Gadget", notes = "Do you want to create a new Gadget?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Gadget.class),
            @ApiResponse(code = 500, message = "Some parameter is null or not valid")
    })
    @Path("/gadget/create")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response newGadget(Gadget newGadget){
        if (newGadget.getId()==null || newGadget.getCost()<0 || newGadget.getDescription()==null || newGadget.getUnity_Shape()==null)  return Response.status(500).entity(newGadget).build();
        this.tm.addGadget(newGadget.getId(),newGadget.getCost(),newGadget.getDescription(),newGadget.getUnity_Shape());
        return Response.status(201).entity(newGadget).build();
    }
    @PUT
    @ApiOperation(value = "buy a Gadget", notes = "Do you want to buy a Gadget?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 409, message = "Wrong credentials."),
            @ApiResponse(code = 401, message = "Gadget does not exist"),
            @ApiResponse(code = 403, message = "You have not enough money ")
    })
    @Path("/gadget/buy/{idGadget}/{idUser}")
    public Response buyAGadget(@PathParam("idGadget")String idGadget,@PathParam("idUser") String idUser) {

        try{
            this.tm.buyGadget(idGadget,idUser);
            return Response.status(201).build();
        }
        catch (NotEnoughMoneyException e){
            return Response.status(403).build();
        }
        catch (IncorrectCredentialsException e) {
            return Response.status(409).build();
        }
        catch (GadgetDoesNotExistException e) {
            return Response.status(401).build();
        }

    }
    @PUT
    @ApiOperation(value = "update a Gadget", notes = "Do you want to update a Gadget?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 401, message = "Gadget does not exist")
    })
    @Path("/gadget/update")
    public Response updateAGadget(Gadget gadget) {
        try{
            this.tm.updateGadget(gadget);
            return Response.status(201).build();
        }
        catch (GadgetDoesNotExistException e) {
            return Response.status(401).build();
        }
    }
    @DELETE
    @ApiOperation(value = "Deletes a gadget", notes = "Deletes a gadget")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Gadget not found")
    })
    @Path("/gadget/delete/{id}")
    public Response deleteGadget(@PathParam("id") String id) {
        try{
            this.tm.deleteGadget(id);
            return Response.status(201).build();
        }
        catch (GadgetDoesNotExistException e) {
            return Response.status(401).build();
        }
    }
}

