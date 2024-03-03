public interface StaffInterface {
    public void addProductionSystem(Production p);
    public void addActorSystem(Actor a);
    public void deleteProduction(Production p);
    public void deleteActor(Actor a);
    public void updateProduction(Production p);
    public void updateActor(Actor a);
    public void resolveUserRequests();
    public void receivedRequest(Request request);
    public void removeReceivedRequest(Request request);
}
