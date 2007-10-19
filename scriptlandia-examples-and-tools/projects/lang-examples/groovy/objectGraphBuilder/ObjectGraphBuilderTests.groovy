/*evaluate("Address.groovy")
evaluate("Invoice.groovy")
evaluate("InvoiceLine.groovy")
evaluate("InvoiceLines.groovy")
evaluate("ObjectGraphBuilder.groovy")
evaluate("PoundsShillingsPence.groovy")
evaluate("Recipient.groovy")
*/

import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;

ScriptlandiaLauncher launcher = ScriptlandiaLauncher.getInstance();

launcher.addClasspathEntry(".");


class ObjectGraphBuilderTests extends GroovyTestCase
{
	
	def builder
	
	void setUp()
	{
		builder = new ObjectGraphBuilder()
	}
	
	void tearDown() 
	{
		builder = null
	}
	
	void testFullGraph()
	{
		def invoice = builder.invoice()
		{
			recipient(name:'Sherlock Holmes')
			{
				address(street:'222b Baker Street', city:'London')
			}
			invoiceLines
			{
				invoiceLine(item:"Deerstalker Hat")
				{
					poundsShillingsPence(pounds:0, shillings:3, pence:10)
				}
				invoiceLine(item:"Tweed Cape")
				{
					poundsShillingsPence(pounds:0, shillings:4, pence:12)
				}
			}
		}
		
		
		assertEquals(Invoice, invoice.class)
		assertEquals(Recipient, invoice.recipient.class)
		assertEquals("Sherlock Holmes", invoice.recipient.name)

		assertEquals(Address, invoice.recipient.address.class)
		assertEquals("222b Baker Street", invoice.recipient.address.street)
		assertEquals("London", invoice.recipient.address.city)
		
		assertEquals(InvoiceLines, invoice.invoiceLines.class)

		assertEquals(2, invoice.invoiceLines.invoiceLines.size())


		def invoiceLine0 = invoice.invoiceLines.invoiceLines[0]

		assertEquals(InvoiceLine, invoiceLine0.class)
		assertEquals("Deerstalker Hat", invoiceLine0.item)
		assertEquals(0, invoiceLine0.poundsShillingsPence.pounds)
		assertEquals(3, invoiceLine0.poundsShillingsPence.shillings)
		assertEquals(10, invoiceLine0.poundsShillingsPence.pence)

		def invoiceLine1 = invoice.invoiceLines.invoiceLines[1]
		
		assertEquals(InvoiceLine, invoiceLine1.class)
		assertEquals("Tweed Cape", invoiceLine1.item)
		assertEquals(0, invoiceLine1.poundsShillingsPence.pounds)
		assertEquals(4, invoiceLine1.poundsShillingsPence.shillings)
		assertEquals(12, invoiceLine1.poundsShillingsPence.pence)
		
	}
	
}