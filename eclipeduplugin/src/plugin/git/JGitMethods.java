package plugin.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import plugin.configuration.Config;

public class JGitMethods {

	public File workDir;
	private Repository gitRepository;
	public Properties properties;

	public JGitMethods(){
		try {
			properties=new Properties();
			properties.load(new FileInputStream("config.properties"));
			System.out.println("Ovo mi nije jasno "+properties.getProperty("Work_repository"));
			workDir=new File(properties.getProperty("Work_repository"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public String gitPull(){
		try {
			try (Git result = Git.cloneRepository()
	                .setURI(properties.getProperty("Remote_repository"))
	                .setDirectory(workDir)
	                .call()) {
		        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
		        System.out.println("Postoji repositorium: " + result.getRepository().getDirectory());

	            // workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=474093
		        result.getRepository().close();
	        }
			return "Projekat je povucen sa git-a";
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Greska. Projekat nije povucen sa git-a";
		}	
	}
	
	/*public String gitPull2(String uri){
		try {
			SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
					  @Override
					  protected void configure( Host host, Session session ) {
					    // do nothing
						//session.setPassword("");
						//session.set
					  }
					  
					  @Override
					  protected JSch createDefaultJSch( FS fs ) throws JSchException {
					  	  JSch defaultJSch = super.createDefaultJSch( fs );
					  	  defaultJSch.addIdentity( "D:/Projects/Project's Java/Fax/VezbeZaDiplomski/kljucevi/studentIS1" );
					  	  return defaultJSch;
					  	}
			};
			UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider("ssh://nastava.pmf.uns.ac.rs/is2", "");
			Git cloneCommand = Git.cloneRepository().setURI(uri).setCredentialsProvider(user)
			.setDirectory(workDir).setCredentialsProvider(new UsernamePasswordCredentialsProvider("git", ""))
			.setTransportConfigCallback( new TransportConfigCallback() {
				@Override
				public void configure( Transport transport ) {
					 SshTransport sshTransport = ( SshTransport )transport;
					 sshTransport.setSshSessionFactory( sshSessionFactory );
				}
			} ).call();
			

			cloneCommand.getRepository().close();
			return "sve ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Lose";
		}
	}*/
	
	public void setGitRepository() throws IOException{
		Git git = Git.open(workDir);
		Repository repo=git.getRepository();
		gitRepository=repo;
	}
	
	public Repository getGitRepository() {
		return gitRepository;
	}
	
	public String gitCreateBranch(String branchName){
		try{
			Git git = new Git(gitRepository);
    		git.branchCreate().setName(branchName).call();
			return "Brach je kreiran sa imenom "+branchName;
		}catch(Exception e){
			e.printStackTrace();;
			return "Doslo je do greske - Branch se nije kreirao!";
		}
	}
	
	public String gitDeleteBranch(String branchName){
		try{
			Git git = new Git(gitRepository);
    		git.branchDelete().setBranchNames(branchName).call();
			return "Brach "+branchName+" je obrisan";
		}catch(Exception e){
			e.printStackTrace();;
			return "Doslo je do greske - Branch se nije kreirao!";
		}
	}
	
	public void gitGetAllBranchs() throws IOException, GitAPIException{
    		Git git = new Git(gitRepository);
            List<Ref> call = git.branchList().call();
            for (Ref ref : call) {
                System.out.println("Branch " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
            }
	}
	
	public String gitCommit(String comment){
		try{
			Git git = new Git(gitRepository);
   		 	git.commit()
    		.setAll(true)
            .setMessage(comment)
            .call();
   		 	return "Commit je izvrsen.";
		}catch(Exception e){
			e.printStackTrace();
			return "Greska. Commit se nije izvrsio!";
		}
	}
	
	public void gitCheckout(String branchName){
		try{
			Git git = new Git(gitRepository);
			System.out.println(git.checkout().setName(branchName).call());
		} catch (JGitInternalException | GitAPIException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gitAllCommit() throws NoHeadException, GitAPIException, IOException{
		Git git = new Git(gitRepository);
		Iterable<RevCommit>commits=git.log().all().call();
		for(RevCommit commit :commits){
			System.out.println("Commit "+commit.getFullMessage()+" "+commit.getId());
			
		}
	}
	
	//Prikaz liste sa remote branch-ovima
	public void gitRemoteRepositoryListRefs() throws InvalidRemoteException, TransportException, GitAPIException{
		Collection<Ref>refs= Git.lsRemoteRepository()
				.setHeads(true)
				.setTags(true)
				.setRemote(properties.getProperty("Remote_repository"))
				.call();
		for(Ref ref:refs){
			System.out.println("Ref : "+ref);
		}
	}
	
	 public boolean gitPush(){
	    	try{
	    		Git git = new Git(gitRepository);
	    		git.push()
	    		.setCredentialsProvider(new UsernamePasswordCredentialsProvider(properties.getProperty("CPUsername"), properties.getProperty("CPPassword")))
	    		.call();
	    		return true;
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return false;
	    	}
	    }
	
		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			JGitMethods gt=new JGitMethods();
			try {
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		
		
			
		}


}

