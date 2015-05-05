package io.fabric8.kubernetes.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class KubernetesListTest {

    @Test
    public void testDefaultValues() throws JsonProcessingException {
        Service service = new ServiceBuilder()
                .withName("test-service")
                .build();
        assertNotNull(service.getApiVersion());
        assertEquals(service.getKind(), "Service");
        
        ReplicationController replicationController = new ReplicationControllerBuilder()
                .withName("test-controller")
                .build();
        assertNotNull(replicationController.getApiVersion());
        assertEquals(replicationController.getKind(), "ReplicationController");
        
        KubernetesList kubernetesList = new KubernetesListBuilder()
                .addNewService()
                    .withName("test-service")
                .and()
                .addNewReplicationController()
                    .withName("test-controller")
                .and()
                .build();
        
        assertNotNull(kubernetesList.getApiVersion());
        assertEquals(kubernetesList.getKind(), "List");
        assertThat(kubernetesList.getItems(), CoreMatchers.hasItem(service));
        assertThat(kubernetesList.getItems(), CoreMatchers.hasItem(replicationController));
    }

    @Test
    public void testInlining() throws JsonProcessingException {
        Service service = new ServiceBuilder()
                .withId("test-service")
                .withNewContainerPort(9091)
                .build();
        
        assertNotNull(service.getApiVersion());
        assertEquals(9091, (int) service.getContainerPort().getIntVal());
        assertEquals(0, (int) service.getContainerPort().getKind());

        service = new ServiceBuilder()
                .withId("test-service")
                .withNewContainerPort("9091")
                .build();

        assertNotNull(service.getApiVersion());
        assertEquals("9091",  service.getContainerPort().getStrVal());
        assertEquals(1, (int) service.getContainerPort().getKind());
    }
}